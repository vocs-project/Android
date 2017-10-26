<?php

namespace VOCS\PlatformBundle\Form;

use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\EmailType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class UserType extends AbstractType
{
    /**
     * {@inheritdoc}
     */
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
            ->add('firstname')
            ->add('surname')
            ->add('email', EmailType::class)
            ->add('password')
            ->add('roles', ChoiceType::class, array(
                'choices' => array(
                    'ROLE_PROFESSOR',
                    'ROLE_STUDENT'
                ),
                'multiple' => true
            ))
            ->add('classes', EntityType::class, array(
                'class' => 'VOCSPlatformBundle:Classes',
                'multiple' => true
            ))
            ->remove('username');
    }
    
    /**
     * {@inheritdoc}
     */
    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults(array(
            'data_class' => 'VOCS\PlatformBundle\Entity\User',
            'csrf_protection' => false
        ));
    }

    /**
     * {@inheritdoc}
     */
    public function getBlockPrefix()
    {
        return 'vocs_platformbundle_user';
    }


}
