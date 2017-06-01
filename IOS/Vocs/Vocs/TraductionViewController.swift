//
//  TraductionViewController.swift
//  Vocs
//
//  Created by Mathis Delaunay on 13/05/2017.
//  Copyright © 2017 Wathis. All rights reserved.
//

import UIKit
import SQLite

class TraductionViewController: UIViewController {

    var textField = VCTextFieldLigneBas(placeholder :"",alignement : .center)
    var validateButton = VCButtonValidate()
    var labelMot = VCLabelMot(text : "")
    var compteur = 0
    let NBR_MOTS_MAX = 10
    var nbrReussi = 0
    var mots : [Mot] = []
    var list : List?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.hideKeyboardWhenTappedAround()
        self.view.backgroundColor = .white
        self.tabBarController?.tabBar.isHidden = true
        self.navigationItem.title = "Traduction"
        self.navigationItem.setLeftBarButton(UIBarButtonItem(title: "Revenir", style: .plain, target: self, action: #selector(handleQuitter)), animated: true)
        validateButton.addTarget(self, action: #selector(handleCheck), for: .touchUpInside)
        setupViews()
        chargerLesMots()
    }
    
    override func viewDidAppear(_ animated: Bool) {
        textField.becomeFirstResponder()
    }
    
    func chargerLesMots() {
        do {
            let fileURL = try! FileManager.default.url(for: .documentDirectory, in: .userDomainMask, appropriateFor: nil, create: false)
                .appendingPathComponent("Vocs.sqlite")
            let db = try Connection("\(fileURL)")
            let word_id = Expression<Int>("id_word")
            let list_id = Expression<Int>("id_list")
            let french = Expression<String>("french")
            let english = Expression<String>("english")
            let words_lists = Table("words_lists")
            let words = Table("words")
            let join_words = words.join(JoinType.leftOuter, words_lists, on: words[word_id] == words_lists[word_id])
            let query = join_words.select(words[word_id],french,english)
                .filter(list_id == (self.list?.id_list)!)
                .order(french, english)
            for word in try db.prepare(query) {
                mots.append(Mot(id: word[word_id], french: word[french], english: word[english]))
            }
        } catch {
            print(error)
            return
        }
        chargerLeMot()
    }
    
    func handleCheck() {
        if !(self.textField.text?.isEmpty)!{
            if let mot = mots[compteur].french?.uppercased() {
                if (mot.contains((self.textField.text?.uppercased())!)){
                    textField.textColor = UIColor(rgb: 0x1ABC9C)
                    nbrReussi += 1;
                } else {
                    textField.textColor = UIColor(rgb: 0xD83333)
                }
            }
        }
        compteur += 1;
        _ = Timer.scheduledTimer(timeInterval: 1, target: self, selector: #selector(chargerLeMot), userInfo: nil, repeats: false)
        textField.isEnabled = false
        validateButton.isEnabled = false
    }
    
    func finir() {
        let controller = ScoreViewController()
        controller.monScore.score.text = String(nbrReussi)
        controller.monScore.maximum.text = String(compteur)
        self.navigationController?.pushViewController(controller, animated: true)
    }
    
    func chargerLeMot(){
        if (compteur == NBR_MOTS_MAX){
            finir()
        }
        if (compteur < mots.count){
            self.labelMot.text = mots[compteur].english
            self.textField.textColor = UIColor(rgb: 0x4A4A4A)
            self.textField.text = ""
        } else {
            finir()
        }
        textField.isEnabled = true
        validateButton.isEnabled = true
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
        self.dismissKeyboard()
        dismiss(animated: true, completion: nil)
    }
}
