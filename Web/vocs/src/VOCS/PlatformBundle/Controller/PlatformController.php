<?php

namespace VOCS\PlatformBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\HttpFoundation\Request;
use VOCS\PlatformBundle\Entity\Words;
use VOCS\PlatformBundle\Form\WordsType;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;

class PlatformController extends Controller
{
    /**
     * @Route("/", name="vocs_platform_homepage")
     */
    public function indexAction()
    {
        return $this->render('VOCSPlatformBundle:Default:index.html.twig');
    }

    /**
     * @Route("/equipe", name="vocs_platform_equipe")
     */
    public function equipeAction()
    {
        return $this->render('VOCSPlatformBundle:Default:equipe.html.twig');
    }

    /**
     * @Route("/apropos", name="vocs_platform_a_propos")
     */
    public function aproposAction()
    {
        return $this->render('VOCSPlatformBundle:Default:apropos.html.twig');
    }

    /**
     * @Route("/connexion", name="vocs_platform_connexion")
     */
    public function connexionAction()
    {
        return $this->render('VOCSPlatformBundle:Default:connexion.html.twig');
    }

}
