<?php

namespace VOCS\PlatformBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Language
 *
 * @ORM\Table(name="language")
 * @ORM\Entity(repositoryClass="VOCS\PlatformBundle\Repository\LanguageRepository")
 */
class Language
{


    /**
     * @var string
     * @ORM\Id
     * @ORM\Column(name="code", type="string", length=255)
     */
    private $code;



    /**
     * Set code
     *
     * @param string $code
     *
     * @return Language
     */
    public function setCode($code)
    {
        $this->code = $code;

        return $this;
    }

    /**
     * Get code
     *
     * @return string
     */
    public function getCode()
    {
        return $this->code;
    }

    function __toString()
    {
        return $this->code;
    }
}

