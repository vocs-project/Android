<?php
namespace VOCS\PlatformBundle\Controller\API;


use FOS\RestBundle\View\View;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Response;
use FOS\RestBundle\Controller\Annotations as Rest;
use VOCS\PlatformBundle\Entity\Language;


class LanguageController extends Controller
{

    /**
     * @Rest\View()
     * @Rest\Get("/rest/languages")
     */
    public function getLanguagesAction(Request $request)
    {
        $languages = $this->getDoctrine()->getRepository(Language::class)->findAll();

        return $languages;
    }
}