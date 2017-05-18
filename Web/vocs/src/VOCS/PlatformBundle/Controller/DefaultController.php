<?php

namespace VOCS\PlatformBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;

class DefaultController extends Controller
{
    public function indexAction()
    {
        return $this->render('VOCSPlatformBundle:Default:index.html.twig');
    }
}
