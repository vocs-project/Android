<?php

namespace VOCS\PlatformBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\HttpFoundation\Request;
use VOCS\PlatformBundle\Entity\Words;
use VOCS\PlatformBundle\Form\WordsType;

class PlatformController extends Controller
{
    public function indexAction()
    {
        return $this->render('VOCSPlatformBundle:Default:index.html.twig');
    }

    public function equipeAction()
    {
        return $this->render('VOCSPlatformBundle:Default:equipe.html.twig');
    }

    public function aproposAction()
    {
        return $this->render('VOCSPlatformBundle:Default:apropos.html.twig');
    }

    public function connexionAction()
    {
        return $this->render('VOCSPlatformBundle:Default:connexion.html.twig');
    }

    public function addAction(Request $request) {

        $word = new Words();
        $trad = new Words();

        $word->setWord('mot');
        $trad->setWord('word');

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
                die;
            }
        }


        $word = $em->find('VOCSPlatformBundle:Words', 13);


        $wordWords = $word->getWords();


        foreach ($wordWords as $wordWord) {
            echo $wordWord->getWord() . '<br>';
        }





        return $this->render('VOCSPlatformBundle:Default:add.html.twig', array(
            'form' => $form->createView(),
            'wordWords' => $wordWords
        ));

    }
}
