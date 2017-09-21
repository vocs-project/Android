<?php

namespace VOCS\PlatformBundle\Entity;

use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints\DateTime;

/**
 * Lists
 *
 * @ORM\Table(name="lists")
 * @ORM\Entity(repositoryClass="VOCS\PlatformBundle\Repository\ListsRepository")
 */
class Lists
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
     * @ORM\Column(name="name", type="string", length=255)
     */
    private $name;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="creationDate", type="datetime")
     */
    private $creationDate;

    /**
     * Many Users have Many Groups.
     * @ORM\ManyToMany(targetEntity="Words")
     * @ORM\JoinTable(name="lists_words",
     *      joinColumns={@ORM\JoinColumn(name="lists_id", referencedColumnName="id")},
     *      inverseJoinColumns={@ORM\JoinColumn(name="words_content", referencedColumnName="content")}
     *      )
     */
    private $words;


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
     * Set name
     *
     * @param string $name
     *
     * @return Lists
     */
    public function setName($name)
    {
        $this->name = $name;

        return $this;
    }

    /**
     * Get name
     *
     * @return string
     */
    public function getName()
    {
        return $this->name;
    }

    /**
     * Set creationDate
     *
     * @param \DateTime $creationDate
     *
     * @return Lists
     */
    public function setCreationDate($creationDate)
    {
        $this->creationDate = $creationDate;

        return $this;
    }

    /**
     * Get creationDate
     *
     * @return \DateTime
     */
    public function getCreationDate()
    {
        return $this->creationDate;
    }
    /**
     * Constructor
     */
    public function __construct()
    {
        $this->words = new \Doctrine\Common\Collections\ArrayCollection();
        $this->setCreationDate(new \DateTime());
    }

    /**
     * Add word
     *
     * @param \VOCS\PlatformBundle\Entity\Words $word
     *
     * @return Lists
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
}
