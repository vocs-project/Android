<?php
namespace VOCS\PlatformBundle\Controller\API;


use FOS\RestBundle\View\View;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Response;
use FOS\RestBundle\Controller\Annotations as Rest;
use VOCS\PlatformBundle\Entity\Language;
use VOCS\PlatformBundle\Entity\User;
use VOCS\PlatformBundle\Form\UserType;


class UserController extends Controller
{

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


}