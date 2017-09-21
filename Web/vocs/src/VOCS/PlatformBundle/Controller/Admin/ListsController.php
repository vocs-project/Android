<?php

namespace VOCS\PlatformBundle\Controller\Admin;


use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\HttpFoundation\Request;
use VOCS\PlatformBundle\Entity\Lists;
use VOCS\PlatformBundle\Form\ListsType;


class ListsController extends Controller
{
    /**
     * @Route("admin/lists/list", name="vocs_platform_admin_lists_list")
     */
    public function listAction() {
        $lists = $this->getDoctrine()->getRepository(Lists::class)->findAll();

        return $this->render('VOCSPlatformBundle:Admin/Lists:lists.html.twig', array(
            'lists' => $lists
        ));
    }

    /**
     * @Route("admin/lists/add", name="vocs_platform_admin_lists_add")
     */
    public function addAction(Request $request) {
        $list = new Lists();

        $form = $this->createForm(ListsType::class, $list)->add('submit', SubmitType::class);

        $form->handleRequest($request);
        $em = $this->getDoctrine()->getManager();

        if($form->isSubmitted()) {
            if($form->isValid()) {
                $em->persist($list);
                $em->flush();
                $this->redirectToRoute('vocs_platform_admin_lists_add');
            }
        }

        return $this->render('VOCSPlatformBundle:Admin/Lists:add.html.twig', array(
            'form' => $form->createView()
        ));
    }

}