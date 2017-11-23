<?php

namespace VOCS\PlatformBundle\Form;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class SchoolsType extends AbstractType
{
    /**
     * {@inheritdoc}
     */
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder->add('uai')->add('type')->add('nom')->add('sigle')->add('universite')->add('cp')->add('commune')->add('communecog')->add('cedex')->add('phone')->add('arrondissement')->add('department')->add('academie')->add('region')->add('regioncog')->add('longitude')->add('latitude')->add('lien');
    }
    
    /**
     * {@inheritdoc}
     */
    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults(array(
            'data_class' => 'VOCS\PlatformBundle\Entity\Schools'
        ));
    }

    /**
     * {@inheritdoc}
     */
    public function getBlockPrefix()
    {
        return 'vocs_platformbundle_schools';
    }


}
