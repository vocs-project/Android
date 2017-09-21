<?php

namespace VOCS\PlatformBundle\Controller\Admin;

use Doctrine\DBAL\Exception\UniqueConstraintViolationException;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;

use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use VOCS\PlatformBundle\Entity\Language;
use VOCS\PlatformBundle\Entity\Words;
use VOCS\PlatformBundle\Form\LanguageType;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use VOCS\PlatformBundle\Form\WordsType;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\Serializer\Encoder\JsonEncoder;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;


class WordsController extends Controller
{

    /**
     * @Route("/admin/words/list", name="vocs_platform_admin_words_list")
     */
    public function listAction(Request $request) {


        $form = $this->createFormBuilder(null, array('attr' => array('id' => 'formLang')))
            ->add('language', EntityType::class, array(
                'class' => Language::class,
                'choice_label' => 'code',
                'required' => false,
                'attr' => array(
                    'onchange' => 'document.getElementById(\'formLang\').submit();'
                )))
            ->getForm();

        $form->handleRequest($request);
        $repWords = $this->getDoctrine()->getRepository(Words::class);
        $words = array();

        if($form->isSubmitted() && $form->getData()['language'] != null) {
            if($form->isValid()) {
                $lang = $form->getData();
                $words = $repWords->findBy(array(
                    'language' => $lang
                ));
            }
        }
        else {
            $words = $repWords->findAll();
        }
        return $this->render('VOCSPlatformBundle:Admin/Words:words.html.twig', array(
            'form' => $form->createView(),
            'words' => $words
        ));
    }

    /**
     * @Route("/admin/words/list/{lang}", name="vocs_platform_admin_words_list_lang")
     */
    public function listLanguageAction(Language $lang) {

        $repWords = $this->getDoctrine()->getRepository(Words::class);
        $words = $repWords->findBy(array(
            'language' => $lang
        ));

        $normalizer = new ObjectNormalizer();
        $normalizer->setCircularReferenceLimit(1);
        $normalizer->setCircularReferenceHandler(function ($object) {
            return $object->getContent(); // Change this to a valid method of your object
        });
        $encoder = new JsonEncoder();

        $serializer = new Serializer(array($normalizer), array($encoder));

        $words = $serializer->serialize($words, 'json');

        $response = new Response($words);

        $response->headers->set('Content-Type', 'application/json');
        return $response;




    }

    /**
     * @Route("/admin/words/add", name="vocs_platform_admin_words_add")
     */
    public function addAction(Request $request) {

        $word = new Words();
        $trad = new Words();

        $word->setContent('mot');
        $trad->setContent('word');

        $form = $this->createFormBuilder()
            ->add('word', WordsType::class, array(
                'data' => $word
            ))
            ->add('trad', WordsType::class, array(
                'data' => $trad
            ))
            ->add('submit', SubmitType::class)
            ->getForm();

        $form->handleRequest($request);
        $em = $this->getDoctrine()->getManager();

        if($form->isSubmitted()) {
            if($form->isValid()) {
                $word->addTrad($trad);
                $trad->addTrad($word);

                $em->persist($word);
                $em->persist($trad);
                $em->flush();

            }
        }


        return $this->render('VOCSPlatformBundle:Default:add.html.twig', array(
            'form' => $form->createView(),

        ));

    }

    public function deleteAction() {

    }

    public function editAction() {

    }
}
