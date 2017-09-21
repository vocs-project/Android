<?php

namespace VOCS\PlatformBundle\Controller\Admin;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;

use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\HttpFoundation\Request;
use VOCS\PlatformBundle\Entity\Language;
use VOCS\PlatformBundle\Form\LanguageType;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;


class LanguageController extends Controller
{
    public function listAction() {

    }

    /**
     * @Route("/admin/language/add", name="vocs_platform_admin_language_add")
     */
    public function addAction(Request $request) {

        $lang = new Language();

        $form = $this->createForm(LanguageType::class)
            ->add('submit', SubmitType::class);

        $form->handleRequest($request);

        if($form->isSubmitted()) {
            if($form->isValid()) {
                $lang = $form->getData();

                $em = $this->getDoctrine()->getManager();

                $em->persist($lang);
                $em->flush();
            }
        }

        return $this->render('VOCSPlatformBundle:Admin/Language:add.html.twig', array(
            'form' => $form->createView()
        ));

    }

    public function deleteAction() {

    }

    public function editAction() {

    }
}
