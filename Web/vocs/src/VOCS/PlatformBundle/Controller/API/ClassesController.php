<?php
namespace VOCS\PlatformBundle\Controller\API;


use FOS\RestBundle\View\View;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Response;
use FOS\RestBundle\Controller\Annotations as Rest;
use VOCS\PlatformBundle\Entity\Classes;
use VOCS\PlatformBundle\Entity\User;
use VOCS\PlatformBundle\Form\ClassesType;
use Nelmio\ApiDocBundle\Annotation\ApiDoc;


class ClassesController extends Controller
{

    /**
     * GET
     */

    /**
     *  @ApiDoc(
     *     description="Récupère toutes les classes",
     *     output= { "class"=Classes::class, "collection"=true, "groups"={"classe"} }
     *     )
     *
     * @Rest\View(serializerGroups={"classe"})
     * @Rest\Get("/rest/classes")
     */
    public function getClassesAction(Request $request)
    {
        $classes = $this->getDoctrine()->getRepository(Classes::class)->findAll();

        $view = View::create($classes);
        $view->setHeader('Access-Control-Allow-Origin', '*');

        return $view;
    }


    /**
     * @ApiDoc(
     *     description="Récupère une classe",
     *     output= { "class"=Classes::class, "collection"=false, "groups"={"classe"} }
     *     )
     *
     * @Rest\View(serializerGroups={"classe"})
     * @Rest\Get("/rest/classes/{id}")
     */
    public function getClasseAction(Request $request)
    {
        $classe = $this->getDoctrine()->getRepository(Classes::class)->find($request->get('id'));

        $view = View::create($classe);
        $view->setHeader('Access-Control-Allow-Origin', '*');

        return $view;
    }

    /**
     * @ApiDoc(
     *     description="Récupère les listes d'une classe",
     *     output= { "class"=Classes::class, "collection"=true, "groups"={"classe"} }
     *     )
     *
     * @Rest\View(serializerGroups={"classe"})
     * @Rest\Get("/rest/classes/{id}/lists")
     */
    public function getClasseListsAction(Request $request)
    {
        $classe = $this->getDoctrine()->getRepository(Classes::class)->find($request->get('id'));
        $lists = $classe->getLists();

        $view = View::create($lists);
        $view->setHeader('Access-Control-Allow-Origin', '*');

        return $view;
    }



    /**
     * POST
     */

    /**
     *  @ApiDoc(
     *    description="Crée une classe",
     *    input={"class"=ClassesType::class, "name"=""}
     * )
     *
     * @Rest\View(statusCode=Response::HTTP_CREATED, serializerGroups={"classe"})
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
            $view = View::create($classe);
            $view->setHeader('Access-Control-Allow-Origin', '*');

            return $view;
        } else {
            $view = View::create($form);
            $view->setHeader('Access-Control-Allow-Origin', '*');

            return $view;
        }

    }

    /**
     * PUT
     */

    /**
     * @ApiDoc(
     *    description="Change une classe",
     *    input={"class"=ClassesType::class, "name"=""}
     * )
     *
     * @Rest\View(serializerGroups={"classe"})
     * @Rest\Put("/rest/classes/{id}")
     */
    public function putClassesAction(Request $request)
    {
        return $this->updateClasse($request, true);
    }

    /**
     * @ApiDoc(
     *    description="Patch une classe",
     *    input={"class"=ClassesType::class, "name"=""}
     * )
     *
     * @Rest\View(serializerGroups={"classe"})
     * @Rest\Patch("/rest/classes/{id}")
     */
    public function patchClassesAction(Request $request)
    {
        return $this->updateClasse($request, false);
    }


    private function updateClasse(Request $request, $clearMissing) {
        $classe = $this->getDoctrine()->getRepository(Classes::class)->find($request->get('id'));

        if (empty($classe)) {
            return new JsonResponse(['message' => 'Classe not found'], Response::HTTP_NOT_FOUND);
        }

        $form = $this->createForm(ClassesType::class, $classe);

        $form->submit($request->request->all(), $clearMissing);


        if ($form->isValid()) {

            $em = $this->getDoctrine()->getManager();
            $em->flush();

            return View::create($classe)->setHeader('Access-Control-Allow-Origin', '*');
        } else {
            return View::create($form)->setHeader('Access-Control-Allow-Origin', '*');
        }
    }

    /**
     * DELETE
     */

    /**
     * @ApiDoc(
     *     description="Remove un user d'une classe",
     *     output= { "class"=Classes::class, "collection"=false, "groups"={"classe"} }
     *     )
     *
     * @Rest\View(serializerGroups={"classe"})
     * @Rest\Delete("/rest/classes/{id}/users/{user_id}")
     */
    public function deleteUserClasse(Request $request) {

        $user = $this->getDoctrine()->getRepository(User::class)->find($request->get('user_id'));
        $classe = $this->getDoctrine()->getRepository(Classes::class)->find($request->get('id'));

        $user->removeClass($classe);
        $classe->removeUser($user);

        $this->getDoctrine()->getManager()->flush();

        $view = View::create($classe);
        $view->setHeader('Access-Control-Allow-Origin', '*');

        return $view;
    }
}