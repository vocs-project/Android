<?php
namespace VOCS\PlatformBundle\Controller\API;


use FOS\RestBundle\View\View;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Response;
use FOS\RestBundle\Controller\Annotations as Rest;
use VOCS\PlatformBundle\Entity\Cities;



class CitiesController extends Controller
{

    /**
     * @Rest\View()
     * @Rest\Get("/rest/cities")
     */
    public function getCitiesAction(Request $request)
    {
        $cities = $this->getDoctrine()->getRepository(Cities::class)->findAll();

        return $cities;
    }

    /**
     * @Rest\View()
     * @Rest\Get("/rest/cities/{code}")
     */
    public function getCitiesCodeAction(Request $request)
    {
        $cities = $this->getDoctrine()->getRepository(Cities::class)->findByVilleCodePostal($request->get('code'));

        return $cities;
    }
}