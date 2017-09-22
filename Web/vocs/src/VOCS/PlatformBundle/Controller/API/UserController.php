<?php
namespace VOCS\PlatformBundle\Controller\API;


use Doctrine\DBAL\Exception\UniqueConstraintViolationException;
use FOS\RestBundle\View\View;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Response;
use FOS\RestBundle\Controller\Annotations as Rest;
use VOCS\PlatformBundle\Entity\Language;
use VOCS\PlatformBundle\Entity\Lists;
use VOCS\PlatformBundle\Entity\User;
use VOCS\PlatformBundle\Entity\Words;
use VOCS\PlatformBundle\Form\LanguageType;
use VOCS\PlatformBundle\Form\ListsType;
use VOCS\PlatformBundle\Form\UserType;
use VOCS\PlatformBundle\Form\WordsType;


class UserController extends Controller
{

    /**
     * GET
     */

    /**
     * @Rest\View()
     * @Rest\Get("/rest/users")
     */
    public function getUsersAction(Request $request)
    {
        $users = $this->getDoctrine()->getRepository(User::class)->findAll();

        return $users;
    }

    /**
     * @Rest\View()
     * @Rest\Get("/rest/users/{id}")
     */
    public function getUserAction(Request $request)
    {
        $user = $this->getDoctrine()->getRepository(User::class)->find($request->get('id'));

        return $user;
    }

    /**
     * @Rest\View()
     * @Rest\Get("/rest/users/{id}/lists")
     */
    public function getListsAction(Request $request)
    {

        $user = $this->getDoctrine()->getRepository(User::class)->find($request->get('id'));
        $lists = $user->getLists();

        $formatted = [];

        foreach ($lists as $list) {
            $formatted[] = ['id' => $list->getId(), 'name' => $list->getName(),];
        }

        $view = View::create($formatted);
        $view->setFormat('json');

        return $view;
    }


    /**
     * @Rest\View()
     * @Rest\Get("/rest/users/{id}/lists/{list_id}")
     */
    public function getUserListAction(Request $request)
    {
        $list = $this->getDoctrine()->getRepository(Lists::class)->getListOfUser($request->get('list_id'), $request->get('id'));

        $words = $list->getWords();
        $wordsArray = [];
        $tradsArray = [];
        foreach ($words as $word) {
            foreach ($word->getTrads() as $trad) {
                $tradsArray[] = ['content' => $trad->getContent(), 'lang' => $trad->getLanguage()->getCode(),];
            }
            $wordsArray[] = ['content' => $word->getContent(), 'lang' => $word->getLanguage()->getCode(), 'trads' => $tradsArray,];
            $tradsArray = null;
        }

        $formatted = ['id' => $list->getId(), 'name' => $list->getName(), 'words' => $wordsArray,];

        // Création d'une vue FOSRestBundle
        $view = View::create($formatted);
        $view->setFormat('json');


        return $view;
    }





    /**
     * POST
     */


    /**
     * @Rest\View(statusCode=Response::HTTP_CREATED)
     * @Rest\Post("/rest/users")
     */
    public function postUsersAction(Request $request)
    {
        $user = new user();
        $form = $this->createForm(UserType::class, $user);

        $form->submit($request->request->all());

        if ($form->isValid()) {

            $em = $this->get('doctrine')->getManager();
            $em->persist($user);
            $em->flush();

            return $user;
        } else {
            return $form;
        }
    }

    /**
     * @Rest\View(statusCode=Response::HTTP_CREATED)
     * @Rest\Post("/rest/users/{id}/lists")
     */
    public function postUsersListsAction(Request $request)
    {
        $list = new Lists();
        $user = $this->getDoctrine()->getRepository(User::class)->find($request->get('id'));

        $form = $this->createForm(ListsType::class, $list);

        $form->submit($request->request->all());

        if ($form->isValid()) {
            $user->addList($list);
            $em = $this->get('doctrine')->getManager();


            $em->persist($user);

            $em->flush();

            return $list;
        } else {
            return $form;
        }
    }

    /**
     * @Rest\View(statusCode=Response::HTTP_CREATED)
     * @Rest\Post("/rest/users/{id}/lists/{list_id}/words")
     */
    public function postUsersListsWordsAction(Request $request)
    {
        $list = $this->getDoctrine()->getRepository(Lists::class)->getListOfUser($request->get('list_id'), $request->get('id'));

        $word = new Words();
        $word->setContent($request->get('word')['content']);
        $lang = $this->getDoctrine()->getRepository(Language::class)->find($request->get('word')['language']);
        $word->setLanguage($lang);


        $trad = new Words();
        $trad->setContent($request->get('trad')['content']);
        $lang = $this->getDoctrine()->getRepository(Language::class)->find($request->get('trad')['language']);
        $trad->setLanguage($lang);




        $em = $this->getDoctrine()->getManager();
        $errors = 0;
        $em->persist($word);
        try {
            $em->flush();
        } catch (UniqueConstraintViolationException $exception) {
            $word = $em->getRepository(Words::class)->find(array('content' => $word->getContent(), 'language' => $word->getLanguage()->getCode()));
            $em = $this->getDoctrine()->resetEntityManager();
            $em = $this->getDoctrine()->getManager();
            $errors++;
        }
        $em->persist($trad);
        try {
            $em->flush();
        } catch (UniqueConstraintViolationException $exception) {
            $trad = $em->getRepository(Words::class)->find(array('content' => $trad->getContent(), 'language' => $trad->getLanguage()->getCode()));
            $em = $this->getDoctrine()->resetEntityManager();
            $em = $this->getDoctrine()->getManager();
            $errors++;
        }


        $word->addTrad($trad);
        $trad->addTrad($word);

        $em->persist($word);
        $em->persist($trad);


        $list->addWord($word);

        $em->persist($list);
        $em->flush();



        $words = $list->getWords();
        $wordsArray = [];
        $tradsArray = [];
        foreach ($words as $word) {
            foreach ($word->getTrads() as $trad) {
                $tradsArray[] = [
                    'content' => $trad->getContent(),
                    'lang' => $trad->getLanguage()->getCode(),
                ];
            }
            $wordsArray[] = [
                'content' => $word->getContent(),
                'lang' => $word->getLanguage()->getCode(),
                'trads' => $tradsArray,
            ];
            $tradsArray = null;
        }

        $formatted = [
            'id' => $list->getId(),
            'name' => $list->getName(),
            'words' => $wordsArray,
        ];

        // Création d'une vue FOSRestBundle
        $view = View::create($formatted);
        $view->setFormat('json');


        return $view;
        //        } else {
        //            return $form;
        //        }
    }







    /**
     * DELETE
     */

    /**
     * @Rest\View()
     * @Rest\Delete("/rest/users/{id}/lists/{list_id}")
     */
    public function deleteUsersListsAction(Request $request)
    {
        $list = $this->getDoctrine()->getRepository(Lists::class)->getListOfUser($request->get('list_id'), $request->get('id'));

        if ($list != null) {
            $em = $this->getDoctrine()->getManager();


            $em->remove($list);

            $em->flush();


            $words = $list->getWords();
            $wordsArray = [];
            $tradsArray = [];
            foreach ($words as $word) {
                foreach ($word->getTrads() as $trad) {
                    $tradsArray[] = ['content' => $trad->getContent(), 'lang' => $trad->getLanguage()->getCode(),];
                }
                $wordsArray[] = ['content' => $word->getContent(), 'lang' => $word->getLanguage()->getCode(), 'trads' => $tradsArray,];
                $tradsArray = null;
            }

            $formatted = ['id' => $list->getId(), 'name' => $list->getName(), 'words' => $wordsArray,];

            // Création d'une vue FOSRestBundle
            $view = View::create($formatted);
            $view->setFormat('json');

            return $view;


        }

        return null;
    }


}