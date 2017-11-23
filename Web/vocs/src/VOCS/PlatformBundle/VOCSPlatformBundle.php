<?php

namespace VOCS\PlatformBundle;

use Symfony\Component\HttpKernel\Bundle\Bundle;

class VOCSPlatformBundle extends Bundle
{
    public function getParent()
    {
        return 'FOSUserBundle';
    }
}
