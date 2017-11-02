<?php

namespace VOCS\PlatformBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Demands
 *
 * @ORM\Table(name="demands")
 * @ORM\Entity(repositoryClass="VOCS\PlatformBundle\Repository\DemandsRepository")
 */
class Demands
{
    /**
     * @ORM\Id
     * @ORM\ManyToOne(targetEntity="User")
     * @ORM\JoinColumn(name="userSend_id", referencedColumnName="id")
     */
    private $userSend;

    /**
     * @ORM\Id
     * @ORM\ManyToOne(targetEntity="User")
     * @ORM\JoinColumn(name="userReceive_id", referencedColumnName="id")
     */
    private $userReceive;

    /**
     * @ORM\Id
     * @ORM\ManyToOne(targetEntity="Classes")
     * @ORM\JoinColumn(name="classe_id", referencedColumnName="id")
     */
    private $classe;

    /**
     * Set userSend
     *
     * @param \VOCS\PlatformBundle\Entity\User $userSend
     *
     * @return Demands
     */
    public function setUserSend(\VOCS\PlatformBundle\Entity\User $userSend = null)
    {
        $this->userSend = $userSend;

        return $this;
    }

    /**
     * Get userSend
     *
     * @return \VOCS\PlatformBundle\Entity\User
     */
    public function getUserSend()
    {
        return $this->userSend;
    }

    /**
     * Set userReceive
     *
     * @param \VOCS\PlatformBundle\Entity\User $userReceive
     *
     * @return Demands
     */
    public function setUserReceive(\VOCS\PlatformBundle\Entity\User $userReceive = null)
    {
        $this->userReceive = $userReceive;

        return $this;
    }

    /**
     * Get userReceive
     *
     * @return \VOCS\PlatformBundle\Entity\User
     */
    public function getUserReceive()
    {
        return $this->userReceive;
    }

    /**
     * Set classe
     *
     * @param \VOCS\PlatformBundle\Entity\Classes $classe
     *
     * @return Demands
     */
    public function setClasse(\VOCS\PlatformBundle\Entity\Classes $classe = null)
    {
        $this->classe = $classe;

        return $this;
    }

    /**
     * Get classe
     *
     * @return \VOCS\PlatformBundle\Entity\Classes
     */
    public function getClasse()
    {
        return $this->classe;
    }
}
