//
//  GameViewController.swift
//  Vocs
//
//  Created by Mathis Delaunay on 13/05/2017.
//  Copyright © 2017 Wathis. All rights reserved.
//

import UIKit

class ExercicesViewController: UIViewController {
    
    var labelBienvenue = VCLabelMenu(text: "Bienvenue sur Vocs",size: 25)
    var boutonTraduction = VCButtonExercice("Traduction",color : UIColor(rgb: 0x1C7FBD))

    override func viewDidLoad() {
        super.viewDidLoad()
        self.hideKeyboardWhenTappedAround()
        self.view.backgroundColor = .white
        self.navigationItem.title = "Exercices"
        self.navigationItem.leftBarButtonItem = UIBarButtonItem(image: #imageLiteral(resourceName: "Parametres"), style: .plain, target: self, action: #selector(handleParametres))
        boutonTraduction.addTarget(self, action: #selector(handleTraduction), for: .touchUpInside)
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
        boutonTraduction.widthAnchor.constraint(equalToConstant : 200).isActive = true
        boutonTraduction.heightAnchor.constraint(equalToConstant: 60).isActive = true
        boutonTraduction.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
        
    }
    
    func handleTraduction() {
        let traduction = ChoisirListeViewController()
        self.navigationController?.pushViewController(traduction, animated: true)
    }
    
    func handleParametres() {
        let reglagesController = ReglagesViewController()
        let navReglageController = UINavigationController(rootViewController: reglagesController)
        present(navReglageController, animated: true, completion: nil)
        
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
}
