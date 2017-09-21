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


class ListsController extends Controller
{

    /**
     * @Rest\View()
     * @Rest\Get("/rest/lists")
     */
    public function getListsAction(Request $request)
    {
        $lists = $this->getDoctrine()->getRepository(Lists::class)->findAll();


        $formatted = [];

        foreach ($lists as $list) {

            $formatted[] = ['id' => $list->getId(), 'name' => $list->getName(),];

        }

        // Création d'une vue FOSRestBundle
        $view = View::create($formatted);
        $view->setFormat('json');

        return $view;
    }

    /**
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

        return $view;
    }


    /**
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

}