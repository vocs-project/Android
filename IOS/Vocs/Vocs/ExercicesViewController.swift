//
//  GameViewController.swift
//  Vocs
//
//  Created by Mathis Delaunay on 13/05/2017.
//  Copyright © 2017 Wathis. All rights reserved.
//

import UIKit

class ExercicesViewController: UIViewController {
    
    var labelBienvenue = VCLabelMenu(text: "Bienvenue sur Vocs")

    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationItem.title = "Exercices"
        self.navigationItem.leftBarButtonItem = UIBarButtonItem(image: #imageLiteral(resourceName: "Parametres"), style: .plain, target: self, action: #selector(appuyerParametres))
        setupViews()
    }
    
    func setupViews() {
        
    }
    
    func appuyerParametres() {
        
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
}
