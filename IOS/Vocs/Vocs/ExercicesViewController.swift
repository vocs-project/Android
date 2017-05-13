//
//  GameViewController.swift
//  Vocs
//
//  Created by Mathis Delaunay on 13/05/2017.
//  Copyright © 2017 Wathis. All rights reserved.
//

import UIKit

class ExercicesViewController: UIViewController {
    
    var labelBienvenue = VCLabelMenu("Bienvenue sur Vocs")
    var boutonTraduction = VCButtonExercice("Traduction")

    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationItem.title = "Exercices"
        self.navigationItem.leftBarButtonItem = UIBarButtonItem(image: #imageLiteral(resourceName: "Parametres"), style: .plain, target: self, action: #selector(appuyerParametres))
        setupViews()
    }
    
    func setupViews() {
        self.view.addSubview(labelBienvenue)
        labelBienvenue.topAnchor.constraint(equalTo: view.topAnchor, constant: 40).isActive = true
        labelBienvenue.widthAnchor.constraint(equalTo: self.view.widthAnchor).isActive = true
        labelBienvenue.heightAnchor.constraint(equalToConstant: 50).isActive = true
        labelBienvenue.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
        
        self.view.addSubview(boutonTraduction)
        boutonTraduction.centerYAnchor.constraint(equalTo: view.centerYAnchor,constant : -20).isActive = true
        boutonTraduction.widthAnchor.constraint(equalToConstant : 150).isActive = true
        boutonTraduction.heightAnchor.constraint(equalToConstant: 60).isActive = true
        boutonTraduction.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
        
    }
    
    func appuyerParametres() {
        
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
}
