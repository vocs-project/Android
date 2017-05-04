//
//  TabBarController.swift
//  Vocs
//
//  Created by Mathis Delaunay on 04/05/2017.
//  Copyright © 2017 Wathis. All rights reserved.
//

import UIKit

class TabBarController: UITabBarController {

    override func viewDidLoad() {
        super.viewDidLoad()
        var arrayViews : [UIViewController] = [createAViewController(controller: UIViewController(), image: #imageLiteral(resourceName: "Manette"))]
        arrayViews.append(createAViewController(controller: UIViewController(), image: #imageLiteral(resourceName: "Profil")))
        arrayViews.append(createAViewController(controller: UIViewController(), image: #imageLiteral(resourceName: "Fiche")))
        
        viewControllers = arrayViews
        self.selectedIndex = 1
    }
    
    private func createAViewController(controller : UIViewController, image : UIImage) -> UINavigationController {
        let controller = controller
        controller.navigationItem.title = "Vocs"
        controller.view.backgroundColor = .white
        let navController = UINavigationController(rootViewController: controller)
        navController.tabBarItem.title = "Vocs"
        navController.tabBarItem.image = image
        navController.tabBarItem.imageInsets = UIEdgeInsets(top: 6, left: 0, bottom: -6, right: 0)
        navController.tabBarItem.title = ""
        return navController
    }
}
