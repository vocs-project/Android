<?php
namespace VOCS\PlatformBundle\Controller\API;


use FOS\RestBundle\View\View;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Response;
use FOS\RestBundle\Controller\Annotations as Rest;
use VOCS\PlatformBundle\Entity\Classes;
use VOCS\PlatformBundle\Form\ClassesType;


class ClassesController extends Controller
{

    /**
     * GET
     */

    /**
     * @Rest\View()
     * @Rest\Get("/rest/classes")
     */
    public function getClassesAction(Request $request)
    {
        $classes = $this->getDoctrine()->getRepository(Classes::class)->findAll();

        return $classes;
    }


    /**
     * @Rest\View()
     * @Rest\Get("/rest/classes/{id}")
     */
    public function getClasseAction(Request $request)
    {
        $classe = $this->getDoctrine()->getRepository(Classes::class)->find($request->get('id'));

        return $classe;
    }


    /**
     * POST
     */

    /**
     * @Rest\View(statusCode=Response::HTTP_CREATED)
     * @Rest\Post("/rest/classes")
     *
     */
    public function postClasseAction(Request $request) {
        $classe = new Classes();

        $form = $this->createForm(ClassesType::class, $classe);

        $form->submit($request->request->all());

        if ($form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            // l'entité vient de la base, donc le merge n'est pas nécessaire.
            // il est utilisé juste par soucis de clarté
            $em->persist($classe);
            $em->flush();
            return $classe;
        } else {
            return $form;
        }

    }

}