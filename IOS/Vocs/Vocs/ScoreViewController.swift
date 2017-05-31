//
//  ScoreViewController.swift
//  Vocs
//
//  Created by Mathis Delaunay on 14/05/2017.
//  Copyright © 2017 Wathis. All rights reserved.
//

import UIKit

class ScoreViewController: UIViewController {

    let monScore = VCScore()
    let buttonQuitter = VCButtonExercice("Quitter", color: UIColor(rgb: 0x1ABC9C))
    let labelCommentaire = VCLabelMenu(text: "Bien joué !",size: 35)
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = .white
        buttonQuitter.addTarget(self, action: #selector(handleQuitter), for: .touchUpInside)
        setupViews()
        self.navigationItem.title = "Traduction"
        self.tabBarController?.tabBar.isHidden = false
        self.navigationItem.hidesBackButton = true
        
        if let score = monScore.score.text, let max = monScore.maximum.text {
            let ratioScore = Double(score)! / Double(max)!
            if ratioScore > 0.5 && ratioScore != 1{
                labelCommentaire.text = "Bien joué !"
            } else if ratioScore == 1{
                labelCommentaire.text = "Parfait !"
            } else if ratioScore <= 0.5 && ratioScore != 0{
                labelCommentaire.text = "Presque !"
            } else {
                labelCommentaire.text = "Dommage !"
            }
        } else {
            labelCommentaire.text = ""
        }
    }
    
    func handleQuitter() {
        let controller = TabBarController()
        controller.modalTransitionStyle = UIModalTransitionStyle.flipHorizontal
        present(controller, animated: true, completion: nil)
    }
    
    func setupViews() {
        self.view.addSubview(monScore)
        
        monScore.centerXAnchor.constraint(equalTo: self.view.centerXAnchor).isActive = true
        monScore.centerYAnchor.constraint(equalTo: self.view.centerYAnchor,constant : -50).isActive = true
        monScore.widthAnchor.constraint(equalToConstant : 100).isActive = true
        monScore.heightAnchor.constraint(equalToConstant : 100).isActive = true
        
        self.view.addSubview(labelCommentaire)
        
        labelCommentaire.centerXAnchor.constraint(equalTo: self.view.centerXAnchor).isActive = true
        labelCommentaire.bottomAnchor.constraint(equalTo: monScore.topAnchor,constant : -50).isActive = true
        labelCommentaire.widthAnchor.constraint(equalTo : self.view.widthAnchor).isActive = true
        labelCommentaire.heightAnchor.constraint(equalToConstant : 50).isActive = true
        
        self.view.addSubview(buttonQuitter)
        buttonQuitter.centerYAnchor.constraint(equalTo: view.centerYAnchor,constant : 100).isActive = true
        buttonQuitter.widthAnchor.constraint(equalToConstant : 200).isActive = true
        buttonQuitter.heightAnchor.constraint(equalToConstant: 60).isActive = true
        buttonQuitter.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
    }

}
