<?php

namespace VOCS\PlatformBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;

class DefaultController extends Controller
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
}
