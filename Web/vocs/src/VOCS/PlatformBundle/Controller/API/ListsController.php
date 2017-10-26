<?php
namespace VOCS\PlatformBundle\Controller\API;

use FOS\RestBundle\View\View;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Response;
use FOS\RestBundle\Controller\Annotations as Rest;

use VOCS\PlatformBundle\Entity\Lists;
use VOCS\PlatformBundle\Form\ListsType;
use Nelmio\ApiDocBundle\Annotation\ApiDoc;

class ListsController extends Controller
{

    /**
     *  @ApiDoc(
     *  description="Récupère toutes les listes",
     *  output= { "class"=Lists::class, "collection"=true,  "groups"={"list"} }
     *  )
     *
     * @Rest\View(serializerGroups={"list"})
     * @Rest\Get("/rest/lists")
     */
    public function getListsAction(Request $request)
    {
        $lists = $this->getDoctrine()->getRepository(Lists::class)->findAll();

        $view = View::create($lists);
        $view->setHeader('Access-Control-Allow-Origin', '*');

        return $view;
    }

    /**
     *  @ApiDoc(
     *  description="Récupère une liste",
     *  output= { "class"=Lists::class, "collection"=false, "groups"={"list"} }
     *  )
     *
     * @Rest\View()
     * @Rest\Get("/rest/lists/{id}")
     */
    public function getListAction(Request $request)
    {
        $list = $this->getDoctrine()->getRepository(Lists::class)->find($request->get('id'));


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
        $view->setHeader('Access-Control-Allow-Origin', '*');

        return $view;
    }



    /**
     * POST
     */

    /**
     * @ApiDoc(
     *    description="Crée une liste",
     *    input={"class"=ListsType::class, "name"=""}
     * )
     *
     * @Rest\View(statusCode=Response::HTTP_CREATED)
     * @Rest\Post("/rest/lists")
     */
    public function postListsAction(Request $request)
    {
        $list = new Lists();
        $em = $this->get('doctrine.orm.entity_manager');
        $list->setName($request->get('name'));
        $em->persist($list);
        $em->flush();

        return $list;
    }

    /**
     * PUT
     */

    /**
     * @ApiDoc(
     *    description="Change une liste",
     *    input={"class"=ListsType::class, "name"=""}
     * )
     *
     * @Rest\View(serializerGroups={"list"})
     * @Rest\Put("/rest/lists/{id}")
     */
    public function putListsAction(Request $request)
    {
        return $this->updateList($request, true);
    }

    /**
     * @ApiDoc(
     *    description="Patch une liste",
     *    input={"class"=ListsType::class, "name"=""}
     * )
     *
     * @Rest\View(serializerGroups={"list"})
     * @Rest\Patch("/rest/lists/{id}")
     */
    public function patchListsAction(Request $request)
    {
        return $this->updateList($request, false);
    }


    private function updateList(Request $request, $clearMissing) {
        $list = $this->getDoctrine()->getRepository(Lists::class)->find($request->get('id'));

        if (empty($list)) {
            return new JsonResponse(['message' => 'List not found'], Response::HTTP_NOT_FOUND);
        }

        $form = $this->createForm(ListsType::class, $list);

        $form->submit($request->request->all(), $clearMissing);


        if ($form->isValid()) {

            $em = $this->getDoctrine()->getManager();
            $em->merge($list);
            $em->flush();

            return View::create($list)->setHeader('Access-Control-Allow-Origin', '*');
        } else {
            return View::create($form)->setHeader('Access-Control-Allow-Origin', '*');
        }
    }

}