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
use VOCS\PlatformBundle\Entity\ListsWords;
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

        $formatted = [];

        foreach ($users as $user) {
            $formatted[] = ['id' => $user->getId(), 'email' => $user->getEmail(), 'firstname' => $user->getFirstname(), 'surname' => $user->getSurname()];
        }

        $view = View::create($formatted);
        $view->setFormat('json');

        return $view;
    }

    /**
     * @Rest\View()
     * @Rest\Get("/rest/users/{id}")
     */
    public function getUserAction(Request $request)
    {
        $user = $this->getDoctrine()->getRepository(User::class)->find($request->get('id'));

        $formatted = ['id' => $user->getId(), 'email' => $user->getEmail(), 'firstname' => $user->getFirstname(), 'surname' => $user->getSurname()];

        $view = View::create($formatted);
        $view->setFormat('json');

        return $view;
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
        $list = $this->getDoctrine()->getRepository(Lists::class)->find($request->get('list_id'));
        $listWords = $this->getDoctrine()->getRepository(ListsWords::class)->findBy(array('list' => $list));


        $wordsArray = [];

        foreach ($listWords as $listWord) {
            $wordTrads = [];
            $tradTrads = [];
            foreach ($listWord->getWord()->getTrads() as $trad) {
                $wordTrads[] = [
                    'content' => $trad->getContent(),
                    'lang' => $trad->getLanguage()->getCode(),
                ];
            }

            foreach ($listWord->getTrad()->getTrads() as $trad) {
                $tradTrads[] = [
                    'content' => $trad->getContent(),
                    'lang' => $trad->getLanguage()->getCode(),
                ];
            }

            $word = [
                'content' => $listWord->getWord()->getContent(),
                'lang' => $listWord->getWord()->getLanguage()->getCode(),
                'trads' => $wordTrads
            ];

            $trad = [
                'content' => $listWord->getTrad()->getContent(),
                'lang' => $listWord->getTrad()->getLanguage()->getCode(),
                'trads' => $tradTrads
            ];

            $wordsArray[] = [
                'word' => $word,
                'trad' => $trad
            ];

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
    }





    /**
     * POST
     */


    /**
     * @Rest\View()
     * @Rest\Post("/rest/users/authentification")
     */
    public function authentificationAction(Request $request)
    {
        $repUser = $this->getDoctrine()->getRepository('VOCSPlatformBundle:User');
        $user = $repUser->findOneBy(array('email' => $request->get('email')));
        if ($user == null || $user->getPassword() != $request->get('password')) {
            $formatted = ['code' => 401, 'message' => 'Authenfication failed'];
        } else {

            $formattedLists = [];

            $lists = $user->getLists();

            foreach ($lists as $list) {
                $formattedLists[] = ['id' => $list->getId(), 'name' => $list->getName(),];
            }

            $formatted = ['id' => $user->getId(), 'email' => $user->getEmail(), 'firstname' => $user->getFirstname(), 'surname' => $user->getSurname(), 'lists' => $formattedLists];

        }
        $view = View::create($formatted);
        $view->setFormat('json');

        return $view;

    }


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
        $em = $this->getDoctrine()->getManager();

        $list = $this->getDoctrine()->getRepository(Lists::class)->getListOfUser($request->get('list_id'), $request->get('id'));

        $repWords = $em->getRepository('VOCSPlatformBundle:Words');

        $word = $repWords->find(array('content' => $request->get('word')['content'], 'language' => $request->get('word')['language']));

        if (!isset($word)) {

            $word = new Words();
            $lang = $em->getRepository('VOCSPlatformBundle:Language')->find($request->get('word')['language']);
            $word->setLanguage($lang);
            $word->setContent($request->get('word')['content']);
            $em->persist($word);
        }

        $trad = $repWords->find(array('content' => $request->get('trad')['content'], 'language' => $request->get('trad')['language']));
        if (!isset($trad)) {
            $trad = new Words();
            $lang = $em->getRepository('VOCSPlatformBundle:Language')->find($request->get('trad')['language']);
            $trad->setLanguage($lang);
            $trad->setContent($request->get('trad')['content']);
            $em->persist($trad);
        }

        if (!$word->getTrads()->contains($trad)) {
            $word->addTrad($trad);
        }

        if (!$trad->getTrads()->contains($word)) {
            $trad->addTrad($word);
        }

        $repListWords = $this->getDoctrine()->getRepository(ListsWords::class);
        $listWord = $repListWords->testListWord($list, $word, $trad);
        if($listWord == null) {
            $listWord = new ListsWords();
            $listWord->setList($list);
            $listWord->setWord($word);
            $listWord->setTrad($trad);
            $em->persist($listWord);
        }



        $em->flush();


        $listWords = $repListWords->findBy(array('list' => $list));


        $wordsArray = [];

        foreach ($listWords as $listWord) {
            $wordTrads = [];
            $tradTrads = [];
            foreach ($listWord->getWord()->getTrads() as $trad) {
                $wordTrads[] = [
                    'content' => $trad->getContent(),
                    'lang' => $trad->getLanguage()->getCode(),
                ];
            }

            foreach ($listWord->getTrad()->getTrads() as $trad) {
                $tradTrads[] = [
                    'content' => $trad->getContent(),
                    'lang' => $trad->getLanguage()->getCode(),
                ];
            }

            $word = [
                'content' => $listWord->getWord()->getContent(),
                'lang' => $listWord->getWord()->getLanguage()->getCode(),
                'trads' => $wordTrads
            ];

            $trad = [
                'content' => $listWord->getTrad()->getContent(),
                'lang' => $listWord->getTrad()->getLanguage()->getCode(),
                'trads' => $tradTrads
            ];

            $wordsArray[] = [
                'word' => $word,
                'trad' => $trad
            ];
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