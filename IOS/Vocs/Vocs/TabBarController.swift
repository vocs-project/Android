//
//  TabBarController.swift
//  Vocs
//
//  Created by Mathis Delaunay on 04/05/2017.
//  Copyright © 2017 Wathis. All rights reserved.
//

import UIKit
import SQLite

class TabBarController: UITabBarController {

    override func viewDidLoad() {
        super.viewDidLoad()
        var arrayViews : [UIViewController] = [createAViewController(controller: ClassesViewController(), image: #imageLiteral(resourceName: "Classe"))]
        arrayViews.append(createAViewController(controller: ExercicesViewController(), image: #imageLiteral(resourceName: "Manette") ))
        arrayViews.append(createAViewController(controller: ListesViewController(), image: #imageLiteral(resourceName: "Liste")))
        self.view.backgroundColor = .white
        viewControllers = arrayViews
        self.selectedIndex = 1
    }
    
    private func createAViewController(controller : UIViewController, image : UIImage) -> UINavigationController {
        let controller = controller
        controller.view.backgroundColor = .white
        let navController = UINavigationController(rootViewController: controller)
        navController.tabBarItem.image = image
        navController.tabBarItem.imageInsets = UIEdgeInsets(top: 6, left: 0, bottom: -6, right: 0)
        return navController
    }
}
