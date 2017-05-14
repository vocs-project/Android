//
//  TraductionViewController.swift
//  Vocs
//
//  Created by Mathis Delaunay on 13/05/2017.
//  Copyright © 2017 Wathis. All rights reserved.
//

import UIKit

class TraductionViewController: UIViewController {

    var textField = VCTextFieldTraduction()
    var validateButton = VCButtonValidate()
    var labelMot = VCLabelMot(text : "Ordinateur")
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.hideKeyboardWhenTappedAround()
        self.view.backgroundColor = .white
        self.tabBarController?.tabBar.isHidden = true
        self.navigationItem.title = "Traduction"
        self.navigationItem.setLeftBarButton(UIBarButtonItem(title: "Revenir", style: .plain, target: self, action: #selector(handleRevenir)), animated: true)
        validateButton.addTarget(self, action: #selector(handleCheck), for: .touchUpInside)
        setupViews()
    }
    
    func handleCheck() {
        if (textField.text?.uppercased() == "Computer".uppercased()){
            textField.textColor = UIColor(rgb: 0x1ABC9C)
        } else {
            
        }
    }
    
    func prochainMot() {
        
    }
    
    
    func setupViews() {
        
        self.view.addSubview(textField)
        textField.centerYAnchor.constraint(equalTo: self.view.centerYAnchor,constant : -40).isActive = true
        textField.heightAnchor.constraint(equalToConstant: 30).isActive = true
        textField.widthAnchor.constraint(equalToConstant: 250).isActive = true
        textField.centerXAnchor.constraint(equalTo: self.view.centerXAnchor,constant : -20).isActive = true
        
        self.view.addSubview(validateButton)
        validateButton.leftAnchor.constraint(equalTo: textField.rightAnchor,constant : 10).isActive = true
        validateButton.heightAnchor.constraint(equalToConstant: 40).isActive = true
        validateButton.widthAnchor.constraint(equalToConstant: 40).isActive = true
        validateButton.centerYAnchor.constraint(equalTo: self.view.centerYAnchor,constant : -40).isActive = true
        
        self.view.addSubview(labelMot)
        labelMot.bottomAnchor.constraint(equalTo: textField.topAnchor,constant : -80).isActive = true
        labelMot.heightAnchor.constraint(equalToConstant: 40).isActive = true
        labelMot.widthAnchor.constraint(equalTo: self.view.widthAnchor).isActive = true
        labelMot.centerXAnchor.constraint(equalTo: self.view.centerXAnchor).isActive = true
    }

    func handleRevenir () {
        self.tabBarController?.tabBar.isHidden = false
        _ = self.navigationController?.popViewController(animated: true)
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
}
