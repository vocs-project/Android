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
     * @Rest\View()
     * @Rest\Get("/rest/schools")
     */
    public function getSchoolsAction(Request $request)
    {
        $schools = $this->getDoctrine()->getRepository(Schools::class)->findAll();

        return $schools;

    }

    /**
     * @Rest\View()
     * @Rest\Get("/rest/schools/cp")
     */
    public function getCpAction(Request $request)
    {
        return $this->getDoctrine()->getRepository(Schools::class)->getCp();
    }

    /**
     * @Rest\View()
     * @Rest\Get("/rest/schools/{cp}")
     */
    public function getSchoolCpAction(Request $request)
    {
        return $this->getDoctrine()->getRepository(Schools::class)->findByCp($request->get('cp'));
    }

    /**
     * @Rest\View()
     * @Rest\Get("/rest/schools/{id}")
     */
    public function getSchoolAction(Request $request)
    {
        $school = $this->getDoctrine()->getRepository(Schools::class)->find($request->get('id'));

        return $school;
    }

    /**
     * PUT
     */

    /**
     * @Rest\View(serializerGroups={"school"})
     * @Rest\Put("/rest/schools/{id}")
     */
    public function putSchoolsAction(Request $request)
    {
        return $this->updateSchool($request, true);
    }

    /**
     * @Rest\View(serializerGroups={"school"})
     * @Rest\Patch("/rest/schools/{id}")
     */
    public function patchSchoolsAction(Request $request)
    {
        return $this->updateSchool($request, false);
    }


    private function updateSchool(Request $request, $clearMissing) {
        $school = $this->getDoctrine()->getRepository(Schools::class)->find($request->get('id'));

        if (empty($school)) {
            return new JsonResponse(['message' => 'School not found'], Response::HTTP_NOT_FOUND);
        }

        $form = $this->createForm(SchoolsType::class, $school);

        $form->submit($request->request->all(), $clearMissing);


        if ($form->isValid()) {

            $em = $this->getDoctrine()->getManager();
            $em->merge($school);
            $em->flush();

            return View::create($school)->setHeader('Access-Control-Allow-Origin', '*');
        } else {
            return View::create($form)->setHeader('Access-Control-Allow-Origin', '*');
        }
    }

}