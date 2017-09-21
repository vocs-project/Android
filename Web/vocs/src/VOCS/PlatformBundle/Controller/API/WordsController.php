<?php
namespace VOCS\PlatformBundle\Controller\API;

use FOS\RestBundle\View\View;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Response;
use FOS\RestBundle\Controller\Annotations as Rest;
use Symfony\Component\Serializer\Encoder\JsonEncoder;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Normalizer\PropertyNormalizer;
use Symfony\Component\Serializer\Serializer;
use VOCS\PlatformBundle\Entity\Words;
use Symfony\Component\Serializer\Normalizer\GetSetMethodNormalizer;
use Symfony\Component\Serializer\Mapping\Loader\AnnotationLoader;
use Symfony\Component\Serializer\Mapping\Factory\ClassMetadataFactory;
use Doctrine\Common\Annotations\AnnotationReader;


class WordsController extends Controller
{

    /**
     * @Rest\View()
     * @Rest\Get("/rest/words")
     */
    public function getWordsAction(Request $request)
    {
        $words = $this->getDoctrine()->getRepository(Words::class)->findAll();
        $formatted = [];
        $trads = [];
        foreach ($words as $word) {
            foreach ($word->getTrads() as $trad) {
                $trads[] = ['content' => $trad->getContent(), 'lang' => $trad->getLanguage()->getCode(),];
            }

            $formatted[] = ['content' => $word->getContent(), 'lang' => $word->getLanguage()->getCode(), 'trads' => $trads,


            ];
            $trads = null;
        }

        // Création d'une vue FOSRestBundle
        $view = View::create($formatted);
        $view->setFormat('json');

        return $view;


    }

    /**
     * @Rest\View()
     * @Rest\Get("/rest/words/{lang}")
     */
    public function getWordsLangAction(Request $request)
    {
        $words = $this->getDoctrine()->getRepository('VOCSPlatformBundle:Words')->findBy(array('language' => $request->get('lang'))); // L'identifiant en tant que paramétre n'est plus nécessaire
        /* @var $place Place */
        $formatted = [];
        $trads = [];
        foreach ($words as $word) {
            foreach ($word->getTrads() as $trad) {
                $trads[] = ['content' => $trad->getContent(), 'lang' => $trad->getLanguage()->getCode(),];
            }

            $formatted[] = ['content' => $word->getContent(), 'lang' => $word->getLanguage()->getCode(), 'trads' => $trads,


            ];
            $trads = null;
        }

        // Création d'une vue FOSRestBundle
        $view = View::create($formatted);
        $view->setFormat('json');

        return $view;


    }

    /**
     * @Rest\View()
     * @Rest\Get("/words/{content}-{lang}")
     */
    public function getWordLangAction(Request $request)
    {
        $word = $this->getDoctrine()->getRepository('VOCSPlatformBundle:Words')->find(array('content' => $request->get('content'), 'language' => $request->get('lang'))); // L'identifiant en tant que paramétre n'est plus nécessaire
        /* @var $place Place */


        if (empty($word)) {
            return new JsonResponse(['message' => 'Word not found'], Response::HTTP_NOT_FOUND);
        }

        $formatted = [];
        $trads = [];

        foreach ($word->getTrads() as $trad) {
            $trads[] = [
                'content' => $trad->getContent(),
                'lang' => $trad->getLanguage()->getCode(),];
        }

        $formatted[] = [
            'content' => $word->getContent(),
            'lang' => $word->getLanguage()->getCode(),
            'trads' => $trads,


        ];
        $trads = null;


        // Création d'une vue FOSRestBundle
        $view = View::create($formatted);
        $view->setFormat('json');

        return $view;

    }
}