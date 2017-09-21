<?php
namespace VOCS\PlatformBundle\Controller\API;


use FOS\RestBundle\View\View;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Response;
use FOS\RestBundle\Controller\Annotations as Rest;
use VOCS\PlatformBundle\Entity\Language;
use VOCS\PlatformBundle\Entity\Lists;
use VOCS\PlatformBundle\Entity\User;
use VOCS\PlatformBundle\Form\ListsType;
use VOCS\PlatformBundle\Form\UserType;


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
                $tradsArray[] = [
                    'content' => $trad->getContent(),
                    'lang' => $trad->getLanguage()->getCode(),]
                ;
            }
            $wordsArray[] = [
                'content' => $word->getContent(),
                'lang' => $word->getLanguage()->getCode(),
                'trads' => $tradsArray,
            ];
            $tradsArray = null;
        }

        $formatted = ['id' => $list->getId(),
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