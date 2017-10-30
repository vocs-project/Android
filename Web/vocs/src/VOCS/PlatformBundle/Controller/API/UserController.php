<?php
namespace VOCS\PlatformBundle\Controller\API;


use Doctrine\DBAL\Exception\UniqueConstraintViolationException;
use FOS\RestBundle\View\View;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Response;
use FOS\RestBundle\Controller\Annotations as Rest;
use VOCS\PlatformBundle\Entity\Classes;
use VOCS\PlatformBundle\Entity\Language;
use VOCS\PlatformBundle\Entity\Lists;
use VOCS\PlatformBundle\Entity\User;
use VOCS\PlatformBundle\Entity\Words;
use VOCS\PlatformBundle\Entity\WordTrad;
use VOCS\PlatformBundle\Form\ClassesType;
use VOCS\PlatformBundle\Form\LanguageType;
use VOCS\PlatformBundle\Form\ListsType;
use VOCS\PlatformBundle\Form\UserType;
use VOCS\PlatformBundle\Form\WordsType;
use Nelmio\ApiDocBundle\Annotation\ApiDoc;
use VOCS\PlatformBundle\Form\WordTradType;


class UserController extends Controller
{

    /**
     * GET
     */

    /**
     * @ApiDoc(
     *     description="Récupère tous les utilisateurs",
     *     output= { "class"=User::class, "collection"=true, "groups"={"user"} }
     *     )
     *
     *
     * @Rest\View(serializerGroups={"user"})
     * @Rest\Get("/rest/users")
     */
    public function getUsersAction(Request $request)
    {
        $users = $this->getDoctrine()->getRepository(User::class)->findAll();

        $view = View::create($users);
        $view->setHeader('Access-Control-Allow-Origin', '*');

        return $view;
    }

    /**
     * @ApiDoc(
     *     description="Récupère un utilisateur",
     *     output= { "class"=User::class, "collection"=false, "groups"={"user"} }
     *     )
     *
     * @Rest\View(serializerGroups={"user"})
     * @Rest\Get("/rest/users/{id}")
     */
    public function getUserAction(Request $request)
    {
        $user = $this->getDoctrine()->getRepository(User::class)->find($request->get('id'));

        $view = View::create($user);
        $view->setHeader('Access-Control-Allow-Origin', '*');

        return $view;

    }

    /**
     * @ApiDoc(
     *     description="Récupère tous les classes d'un utilisateur",
     *     output= { "class"=Classes::class, "collection"=true, "groups"={"classe"} }
     *     )
     *
     *
     * @Rest\View(serializerGroups={"classe"})
     * @Rest\Get("/rest/users/{id}/classes")
     */
    public function getClassesAction(Request $request)
    {

        $user = $this->getDoctrine()->getRepository(User::class)->find($request->get('id'));
        $classes = $user->getClasses();

        return $classes;
    }

    /**
     *@ApiDoc(
     *     description="Récupère tous les listes d'un utilisateur",
     *     output= { "class"=Listes::class, "collection"=true, "groups"={"list"} }
     *     )
     *
     * @Rest\View(serializerGroups={"list"})
     * @Rest\Get("/rest/users/{id}/lists")
     */
    public function getListsAction(Request $request)
    {

        $user = $this->getDoctrine()->getRepository(User::class)->find($request->get('id'));
        $lists = $user->getLists();
        $view = View::create($lists);
        $view->setHeader('Access-Control-Allow-Origin', '*');
        return $view;
    }


    /**
     * @ApiDoc(
     *     description="Récupère une liste d'un utilisateur",
     *     output= { "class"=Listes::class, "collection"=false, "groups"={"list"} }
     *     )
     *
     * @Rest\View(serializerGroups={"list"})
     * @Rest\Get("/rest/users/{id}/lists/{list_id}")
     */
    public function getUserListAction(Request $request)
    {
        $list = $this->getDoctrine()->getRepository(Lists::class)->find($request->get('list_id'));

        $user = $this->getDoctrine()->getRepository(User::class)->find($request->get('id'));


        if($user->getLists()->contains($list)) {
            $view = View::create($list);
        }
        else {
            $reponse = [
                "code" => "404",
                "message" => "l'user " . $request->get('id') . " n'a pas de liste " . $request->get('list_id')
            ];
            $view = View::create($reponse);
            $view->setStatusCode(404);
        }

        $view->setHeader('Access-Control-Allow-Origin', '*');


        return $view;
    }





    /**
     * POST
     */


    /**
     *
     *
     * @Rest\View()
     * @Rest\Post("/rest/users/authentification")
     */
    public function authentificationAction(Request $request)
    {
        $repUser = $this->getDoctrine()->getRepository('VOCSPlatformBundle:User');
        $user = $repUser->findOneBy(array('email' => $request->get('email')));
        $view = View::create();
        if ($user == null || $user->getPassword() != $request->get('password')) {
            $formatted = ['code' => 401, 'message' => 'Authenfication failed'];
            $view->setStatusCode(401);
        } else {

            $formattedLists = [];

            $lists = $user->getLists();

            foreach ($lists as $list) {
                $formattedLists[] = ['id' => $list->getId(), 'name' => $list->getName(),];
            }

            $formatted = ['id' => $user->getId(), 'email' => $user->getEmail(), 'firstname' => $user->getFirstname(), 'surname' => $user->getSurname(), 'lists' => $formattedLists];

        }
        return $view->setData($formatted)->setHeader('Access-Control-Allow-Origin', '*');

    }


    /**
     * @ApiDoc(
     *    description="Crée un utilisateur",
     *    input={"class"=UserType::class, "name"=""}
     * )
     *
     * @Rest\View(statusCode=Response::HTTP_CREATED, serializerGroups={"user"})
     * @Rest\Post("/rest/users")
     */
    public function postUsersAction(Request $request)
    {
        $user = new user();
        $form = $this->createForm(UserType::class, $user);

        $form->submit($request->request->all());

        if ($form->isValid()) {

            $em = $this->get('doctrine')->getManager();
            $em->persist($user);
            $em->flush();

            return View::create($user)->setHeader('Access-Control-Allow-Origin', '*');
        } else {
            return View::create($form)->setHeader('Access-Control-Allow-Origin', '*');
        }
    }

    /**
     * @ApiDoc(
     *    description="Crée une liste à un utilisateur",
     *    input={"class"=ListsType::class, "name"=""}
     * )
     *
     * @Rest\View(statusCode=Response::HTTP_CREATED, serializerGroups={"list"})
     * @Rest\Post("/rest/users/{id}/lists")
     */
    public function postUsersListsAction(Request $request)
    {
        $list = new Lists();
        $user = $this->getDoctrine()->getRepository(User::class)->find($request->get('id'));

        $form = $this->createForm(ListsType::class, $list);

        $form->submit($request->request->all());

        if ($form->isValid()) {
            $user->addList($list);
            $em = $this->getDoctrine()->getManager();

            $em->persist($list);

            $em->flush();

            return View::create($list)->setHeader('Access-Control-Allow-Origin', '*');
        } else {
            return View::create($form)->setHeader('Access-Control-Allow-Origin', '*');
        }
    }

    /**
     *  @ApiDoc(
     *    description="Crée une classe à un utilisateur (professeur)",
     *    input={"class"=ListsType::class, "name"=""}
     * )
     *
     * @Rest\View(statusCode=Response::HTTP_CREATED, serializerGroups={"classe"})
     * @Rest\Post("/rest/users/{id}/classes")
     */
    public function postUsersClassesAction(Request $request)
    {
        $classe = new Classes();
        $user = $this->getDoctrine()->getRepository(User::class)->find($request->get('id'));

        $form = $this->createForm(ClassesType::class, $classe);

        $form->submit($request->request->all());

        if ($form->isValid()) {
            $user->addClass($classe);
            $em = $this->getDoctrine()->getManager();

            $em->persist($classe);

            $em->flush();

            return View::create($classe)->setHeader('Access-Control-Allow-Origin', '*');
        } else {
            return View::create($form)->setHeader('Access-Control-Allow-Origin', '*');
        }
    }

    /**
     *   @ApiDoc(
     *    description="Crée/ajoute un mot dans une liste d'un utilisateur",
     *    input={"class"=WordTradType::class, "name"=""}
     * )
     *
     * @Rest\View(statusCode=Response::HTTP_CREATED, serializerGroups={"list"})
     * @Rest\Post("/rest/users/{id}/lists/{list_id}/wordTrad")
     */
    public function postUsersListsWordsAction(Request $request)
    {
        $list = $this->getDoctrine()->getRepository(Lists::class)->find($request->get('list_id'));

        $user = $this->getDoctrine()->getRepository(User::class)->find($request->get('id'));


        if($user->getLists()->contains($list)) {
            $wordTrad = new WordTrad();

            $form = $this->createForm(WordTradType::class, $wordTrad);

            $form->submit($request->request->all());

            if ($form->isValid()) {
                $em = $this->getDoctrine()->getManager();

                $repWord = $em->getRepository(Words::class);
                $word = $repWord->find(array('content' => $wordTrad->getWord()->getContent(), 'language' => $wordTrad->getWord()->getLanguage()));
                if($word != null) {
                    $wordTrad->setWord($word);
                }
                $trad = $repWord->find(array('content' => $wordTrad->getTrad()->getContent(), 'language' => $wordTrad->getTrad()->getLanguage()));
                if($trad != null) {
                    $wordTrad->setTrad($trad);
                }

                $list->addWordTrad($wordTrad);
                $em->persist($wordTrad);
                $em->flush();
                $view = View::create($list);


            } else {
                $view = View::create($form);
            }

        }
        else {
            $reponse = [
                "code" => "404",
                "message" => "l'user " . $request->get('id') . " n'a pas de liste " . $request->get('list_id')
            ];
            $view = View::create($reponse);
            $view->setStatusCode(404);
        }


        $view->setHeader('Access-Control-Allow-Origin', '*');


        return $view;
    }







    /**
     * DELETE
     */

    /**
     *@ApiDoc(
     *     description="Delete une liste d'un utilisateur (à changer)",
     *     output= { "class"=Lists::class, "collection"=false, "groups"={"list"} }
     *     )
     *
     * @Rest\View()
     * @Rest\Delete("/rest/users/{id}/lists/{list_id}")
     */
    public function deleteUsersListsAction(Request $request)
    {
        $list = $this->getDoctrine()->getRepository(Lists::class)->getListOfUser($request->get('list_id'), $request->get('id'));

        if ($list != null) {
            $em = $this->getDoctrine()->getManager();


            $em->remove($list);

            $em->flush();


            $words = $list->getWords();
            $wordsArray = [];
            $tradsArray = [];
            foreach ($words as $word) {
                foreach ($word->getTrads() as $trad) {
                    $tradsArray[] = ['content' => $trad->getContent(), 'lang' => $trad->getLanguage()->getCode(),];
                }
                $wordsArray[] = ['content' => $word->getContent(), 'lang' => $word->getLanguage()->getCode(), 'trads' => $tradsArray,];
                $tradsArray = null;
            }

            $formatted = ['id' => $list->getId(), 'name' => $list->getName(), 'words' => $wordsArray,];

            // Création d'une vue FOSRestBundle
            return View::create($formatted)->setHeader('Access-Control-Allow-Origin', '*');


        }

        return null;
    }

    /**
     * @ApiDoc(
     *     description="Remove une classe à un user",
     *     output= { "class"=User::class, "collection"=false, "groups"={"user"} }
     *     )
     *
     * @Rest\View(serializerGroups={"user"})
     * @Rest\Delete("/rest/users/{id}/classes/{classe_id}")
     */
    public function deleteUserClasse(Request $request) {

        $user = $this->getDoctrine()->getRepository(User::class)->find($request->get('id'));
        $classe = $this->getDoctrine()->getRepository(Classes::class)->find($request->get('classe_id'));
        if($user->getClasses()->contains($classe)) {
            $user->removeClass($classe);
            $classe->removeUser($user);

            $this->getDoctrine()->getManager()->flush();
            $view = View::create($user);
        }else {
            $response = [
                "code" => 404,
                "message" => "L'user " . $user->getId() . " n'a pas la classe " . $classe->getId(),
            ];
            $view = View::create($response)->setStatusCode(404);
        }



        $view->setHeader('Access-Control-Allow-Origin', '*');

        return $view;
    }


    /**
     * PUT
     */

    /**
     * @ApiDoc(
     *    description="Change un utilisateur",
     *    input={"class"=UserType::class, "name"=""}
     * )
     *
     * @Rest\View(serializerGroups={"user"})
     * @Rest\Put("/rest/users/{id}")
     */
    public function putUsersAction(Request $request)
    {
        return $this->updateUser($request, true);
    }

    /**
     *  @ApiDoc(
     *    description="Patch un utilisateur",
     *    input={"class"=UserType::class, "name"=""}
     * )
     *
     * @Rest\View(serializerGroups={"user"})
     * @Rest\Patch("/rest/users/{id}")
     */
    public function patchUsersAction(Request $request)
    {
        return $this->updateUser($request, false);
    }


    private function updateUser(Request $request, $clearMissing) {
        $user = $this->getDoctrine()->getRepository(User::class)->find($request->get('id'));

        if (empty($user)) {
            return new JsonResponse(['message' => 'User not found'], Response::HTTP_NOT_FOUND);
        }

        $form = $this->createForm(UserType::class, $user);

        $form->submit($request->request->all(), $clearMissing);


        if ($form->isValid()) {

            $em = $this->getDoctrine()->getManager();
            $em->merge($user);
            $em->flush();

            return View::create($user)->setHeader('Access-Control-Allow-Origin', '*');
        } else {
            return View::create($form)->setHeader('Access-Control-Allow-Origin', '*');
        }
    }

}