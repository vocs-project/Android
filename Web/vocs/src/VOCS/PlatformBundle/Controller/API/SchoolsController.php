<?php
namespace VOCS\PlatformBundle\Controller\API;


use FOS\RestBundle\View\View;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Response;
use FOS\RestBundle\Controller\Annotations as Rest;
use VOCS\PlatformBundle\Entity\Lists;
use VOCS\PlatformBundle\Entity\Schools;
use VOCS\PlatformBundle\Form\SchoolsType;
use Nelmio\ApiDocBundle\Annotation\ApiDoc;

class SchoolsController extends Controller
{

    /**
     * GET
     */

    /**
     *  @ApiDoc(
     *     description="Récupère toutes les schools",
     *     output= { "class"=Schools::class, "collection"=true, "groups"={"school"} }
     *     )
     *
     * @Rest\View(serializerGroups={"school"})
     * @Rest\Get("/rest/schools")
     */
    public function getSchoolsAction(Request $request)
    {
        $schools = $this->getDoctrine()->getRepository(Schools::class)->findAll();

        return $schools;

    }


    /**
     * @ApiDoc(
     *     description="Récupère toutes les schools d'un cp",
     *     output= { "class"=Schools::class, "collection"=true, "groups"={"school"} }
     *     )
     *
     * @Rest\View(serializerGroups={"school"})
     * @Rest\Get("/rest/schools/{cp}")
     */
    public function getSchoolCpAction(Request $request)
    {
        return $this->getDoctrine()->getRepository(Schools::class)->findByCp($request->get('cp'));
    }

    /**
     * @ApiDoc(
     *     description="Récupère une school",
     *     output= { "class"=Schools::class, "collection"=false, "groups"={"school"} }
     *     )
     *
     * @Rest\View(serializerGroups={"school"})
     * @Rest\Get("/rest/schools/{id}")
     */
    public function getSchoolAction(Request $request)
    {
        $school = $this->getDoctrine()->getRepository(Schools::class)->find($request->get('id'));

        return $school;
    }

}