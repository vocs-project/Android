<?php

namespace VOCS\PlatformBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * ListsWords
 *
 * @ORM\Table(name="lists_words")
 * @ORM\Entity(repositoryClass="VOCS\PlatformBundle\Repository\ListsWordsRepository")
 */
class ListsWords
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
     * @ORM\ManyToOne(targetEntity="Lists")
     * @ORM\JoinColumn(nullable=false)
     */
    private $list;

    /**
     * @ORM\ManyToOne(targetEntity="Words")
     * @ORM\JoinColumns(
     *     {
     *          @ORM\JoinColumn(name="word_content", referencedColumnName="content"),
     *              @ORM\JoinColumn(name="word_language", referencedColumnName="language_code"),
     *     })
     */
    private $word;

    /**
     * @ORM\ManyToOne(targetEntity="Words")
     * @ORM\JoinColumns(
     *     {
     *          @ORM\JoinColumn(name="trad_content", referencedColumnName="content"),
     *              @ORM\JoinColumn(name="trad_language", referencedColumnName="language_code"),
     *     })
     */
    private $trad;



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
     * Set list
     *
     * @param \VOCS\PlatformBundle\Entity\Lists $list
     *
     * @return ListsWords
     */
    public function setList(\VOCS\PlatformBundle\Entity\Lists $list)
    {
        $this->list = $list;

        return $this;
    }

    /**
     * Get list
     *
     * @return \VOCS\PlatformBundle\Entity\Lists
     */
    public function getList()
    {
        return $this->list;
    }

    /**
     * Set word
     *
     * @param \VOCS\PlatformBundle\Entity\Words $word
     *
     * @return ListsWords
     */
    public function setWord(\VOCS\PlatformBundle\Entity\Words $word)
    {
        $this->word = $word;

        return $this;
    }

    /**
     * Get word
     *
     * @return \VOCS\PlatformBundle\Entity\Words
     */
    public function getWord()
    {
        return $this->word;
    }

    /**
     * Set trad
     *
     * @param \VOCS\PlatformBundle\Entity\Words $trad
     *
     * @return ListsWords
     */
    public function setTrad(\VOCS\PlatformBundle\Entity\Words $trad)
    {
        $this->trad = $trad;

        return $this;
    }

    /**
     * Get trad
     *
     * @return \VOCS\PlatformBundle\Entity\Words
     */
    public function getTrad()
    {
        return $this->trad;
    }
}
