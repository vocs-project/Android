<?php

namespace VOCS\PlatformBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Words
 *
 * @ORM\Table(name="words")
 * @ORM\Entity(repositoryClass="VOCS\PlatformBundle\Repository\WordsRepository")
 */
class Words
{
    /**
     * @var int
     *
     * @ORM\Column(name="id", type="integer")
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="AUTO")
     */
    private $id;

    /**
     * @var string
     *
     * @ORM\Column(name="word", type="string", length=255)
     */
    private $word;

    /**
     * @var string
     *
     * @ORM\Column(name="url_prononciation", type="string", length=255)
     */
    private $urlPrononciation;


    /**
     * Get id
     *
     * @return int
     */
    public function getId()
    {
        return $this->id;
    }

    /**
     * Set word
     *
     * @param string $word
     *
     * @return Words
     */
    public function setWord($word)
    {
        $this->word = $word;

        return $this;
    }

    /**
     * Get word
     *
     * @return string
     */
    public function getWord()
    {
        return $this->word;
    }

    /**
     * Set urlPrononciation
     *
     * @param string $urlPrononciation
     *
     * @return Words
     */
    public function setUrlPrononciation($urlPrononciation)
    {
        $this->urlPrononciation = $urlPrononciation;

        return $this;
    }

    /**
     * Get urlPrononciation
     *
     * @return string
     */
    public function getUrlPrononciation()
    {
        return $this->urlPrononciation;
    }
}

