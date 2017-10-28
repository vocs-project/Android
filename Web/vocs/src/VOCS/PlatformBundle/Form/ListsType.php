<?php

namespace VOCS\PlatformBundle\Form;

use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use VOCS\PlatformBundle\Entity\Words;
use Symfony\Component\Validator\Constraints as Assert;
use VOCS\PlatformBundle\Entity\WordTrad;

class ListsType extends AbstractType
{
    /**
     * {@inheritdoc}
     */
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
            ->add('name')
            ->add('users', EntityType::class, array(
                'class' => 'VOCSPlatformBundle:User',
                'multiple' => true,
                'by_reference' => false
            ))
            ->add('wordTrads', EntityType::class, array(
                'class' => 'VOCSPlatformBundle:WordTrad',
                'multiple' => true,
                'by_reference' => false
            ));
    }
    
    /**
     * {@inheritdoc}
     */
    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults(array(
            'data_class' => 'VOCS\PlatformBundle\Entity\Lists',
            'csrf_protection' => false
        ));
    }

    /**
     * {@inheritdoc}
     */
    public function getBlockPrefix()
    {
        return 'vocs_platformbundle_lists';
    }


}
