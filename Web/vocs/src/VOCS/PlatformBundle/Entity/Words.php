<?php

namespace VOCS\PlatformBundle\Entity;

use Doctrine\ORM\Mapping as ORM;
use JMS\Serializer\Annotation\ExclusionPolicy;
use JMS\Serializer\Annotation\Expose;
use JMS\Serializer\Annotation\Groups;
use JMS\Serializer\Annotation\VirtualProperty;

/**
 * Words
 *
 * @ORM\Table(name="words")
 * @ORM\Entity(repositoryClass="VOCS\PlatformBundle\Repository\WordsRepository")
 *
 * @ExclusionPolicy("all")
 */
class Words
{

    /**
     * @var string
     *
     * @ORM\Column(name="url_prononciation", type="string", length=255, nullable=true)
     */
    private $urlPrononciation;

    /**
     * @var string
     * @ORM\Id
     * @ORM\Column(name="content", type="string", length=255)
     * @Expose
     */
    private $content;

    /**
     * @ORM\Id
     * @ORM\ManyToOne(targetEntity="Language", cascade={"persist"})
     * @ORM\JoinColumn(name="language_code", referencedColumnName="code")
     * @Expose
     */
    private $language;



    /**
     *
     * @ORM\ManyToMany(targetEntity="Words", mappedBy="trads", cascade={"persist"})
     */
    private $words;

    /**
     *
     * @ORM\ManyToMany(targetEntity="Words", inversedBy="words", cascade={"persist"})
     * @ORM\JoinTable(name="traductions",
     *      joinColumns={
     *
     *              @ORM\JoinColumn(name="words_content", referencedColumnName="content"),
     *              @ORM\JoinColumn(name="words_language", referencedColumnName="language_code"),
     *
     *
     *      },
     *      inverseJoinColumns={
     *
     *              @ORM\JoinColumn(name="trad_word_language", referencedColumnName="language_code"),
     *              @ORM\JoinColumn(name="trad_word_content", referencedColumnName="content"),
     *
     *
     *      }
     *      )
     * @Expose
     */
    private $trads;





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


    /**
     * Constructor
     */
    public function __construct()
    {
        $this->words = new \Doctrine\Common\Collections\ArrayCollection();
        $this->trads = new \Doctrine\Common\Collections\ArrayCollection();
    }

    /**
     * Add word
     *
     * @param \VOCS\PlatformBundle\Entity\Words $word
     *
     * @return Words
     */
    public function addWord(\VOCS\PlatformBundle\Entity\Words $word)
    {
        $this->words[] = $word;

        return $this;
    }

    /**
     * Remove word
     *
     * @param \VOCS\PlatformBundle\Entity\Words $word
     */
    public function removeWord(\VOCS\PlatformBundle\Entity\Words $word)
    {
        $this->words->removeElement($word);
    }

    /**
     * Get words
     *
     * @return \Doctrine\Common\Collections\Collection
     */
    public function getWords()
    {
        return $this->words;
    }

    /**
     * Add trad
     *
     * @param \VOCS\PlatformBundle\Entity\Words $trad
     *
     * @return Words
     */
    public function addTrad(\VOCS\PlatformBundle\Entity\Words $trad)
    {
        $this->trads[] = $trad;

        return $this;
    }

    /**
     * Remove trad
     *
     * @param \VOCS\PlatformBundle\Entity\Words $trad
     */
    public function removeTrad(\VOCS\PlatformBundle\Entity\Words $trad)
    {
        $this->trads->removeElement($trad);
    }

    /**
     * Get trads
     *
     * @return \Doctrine\Common\Collections\Collection
     */
    public function getTrads()
    {
        return $this->trads;
    }

    /**
     * Set language
     *
     * @param \VOCS\PlatformBundle\Entity\Language $language
     *
     * @return Words
     */
    public function setLanguage(\VOCS\PlatformBundle\Entity\Language $language = null)
    {
        $this->language = $language;

        return $this;
    }

    /**
     * Get language
     *
     * @return \VOCS\PlatformBundle\Entity\Language
     */
    public function getLanguage()
    {
        return $this->language;
    }

    /**
     * Set content
     *
     * @param string $content
     *
     * @return Words
     */
    public function setContent($content)
    {
        $this->content = $content;

        return $this;
    }

    /**
     * Get content
     *
     * @return string
     */
    public function getContent()
    {
        return $this->content;
    }

    function __toString()
    {
        return $this->content;
    }
}
