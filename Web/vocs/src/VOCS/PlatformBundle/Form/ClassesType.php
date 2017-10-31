<?php

namespace VOCS\PlatformBundle\Form;


use Nelmio\ApiDocBundle\Tests\Fixtures\Form\CollectionType;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use VOCS\PlatformBundle\Entity\Lists;
use VOCS\PlatformBundle\Entity\Schools;

class ClassesType extends AbstractType
{
    /**
     * {@inheritdoc}
     */
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
            ->add('name', TextType::class)
            ->add('urlAvatar', TextType::class)
            ->add('school', EntityType::class,  array(
                'class' => Schools::class,
            ))
            ->add('lists', EntityType::class, array(
                'class' => 'VOCSPlatformBundle:Lists',
                'multiple' => true,
                'by_reference' => false
            ))
            ->add('users', EntityType::class, array(
                'class' => 'VOCSPlatformBundle:User',
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
            'data_class' => 'VOCS\PlatformBundle\Entity\Classes',
            'csrf_protection' => false
        ));
    }

    /**
     * {@inheritdoc}
     */
    public function getBlockPrefix()
    {
        return 'vocs_platformbundle_classes';
    }


}
