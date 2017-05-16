//
//  TraductionViewController.swift
//  Vocs
//
//  Created by Mathis Delaunay on 13/05/2017.
//  Copyright © 2017 Wathis. All rights reserved.
//

import UIKit

class TraductionViewController: UIViewController {

    var textField = VCTextFieldLigneBas(placeholder :"",alignement : .center)
    var validateButton = VCButtonValidate()
    var labelMot = VCLabelMot(text : "Ordinateur")
    var nbrDeMots = 0
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.hideKeyboardWhenTappedAround()
        self.view.backgroundColor = .white
        self.tabBarController?.tabBar.isHidden = true
        self.navigationItem.title = "Traduction"
        self.navigationItem.setLeftBarButton(UIBarButtonItem(title: "Revenir", style: .plain, target: self, action: #selector(handleQuitter)), animated: true)
        validateButton.addTarget(self, action: #selector(handleCheck), for: .touchUpInside)
        setupViews()
    }
    
    override func viewDidAppear(_ animated: Bool) {
        textField.becomeFirstResponder()
    }
    
    func handleCheck() {
        if (textField.text?.uppercased() == "Computer".uppercased()){
            textField.textColor = UIColor(rgb: 0x1ABC9C)
        } else {
            textField.textColor = UIColor(rgb: 0xD83333)
        }
        self.dismissKeyboard()
        _ = Timer.scheduledTimer(timeInterval: 1, target: self, selector: #selector(prochaineView), userInfo: nil, repeats: false)
        validateButton.isEnabled = false
    }
    
    func prochaineView() {
        if nbrDeMots >= 3 {
            let controller = ScoreViewController()
            self.navigationController?.pushViewController(controller, animated: true)
        } else {
            let controller = TraductionViewController()
            controller.nbrDeMots = self.nbrDeMots + 1
            self.navigationController?.pushViewController(controller, animated: true)
        }
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

    func handleQuitter () {
        self.tabBarController?.tabBar.isHidden = false
        let controller = TabBarController()
        presentRightToLeft(controller)
    }
}
