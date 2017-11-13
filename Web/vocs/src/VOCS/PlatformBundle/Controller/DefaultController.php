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

    public function inscription2Action()
    {
        return $this->render('VOCSPlatformBundle:Default:inscription2.html.twig');
    }

    public function elevehomeAction()
    {
        return $this->render('VOCSPlatformBundle:Default:elevehome.html.twig');
    }

    public function elevehomejeuxAction()
    {
        return $this->render('VOCSPlatformBundle:Default:elevehomejeux.html.twig');
    }

    public function elevehomelistesAction()
    {
        return $this->render('VOCSPlatformBundle:Default:elevehomelistes.html.twig');
    }

    public function elevehomereglagesAction()
    {
        return $this->render('VOCSPlatformBundle:Default:elevehomereglages.html.twig');
    }

    public function profhomeAction()
    {
        return $this->render('VOCSPlatformBundle:Default:profhome.html.twig');
    }

    public function profhomelistesAction()
    {
        return $this->render('VOCSPlatformBundle:Default:profhomelistes.html.twig');
    }

    public function jeuclassiqueAction()
    {
        return $this->render('VOCSPlatformBundle:Default:jeuclassique.html.twig');
    }
}
