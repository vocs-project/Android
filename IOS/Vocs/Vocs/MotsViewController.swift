//
//  MotsViewController.swift
//  Vocs
//
//  Created by Mathis Delaunay on 14/05/2017.
//  Copyright © 2017 Wathis. All rights reserved.
//

import UIKit
import SQLite

class MotsViewController: UIViewController , UITableViewDelegate, UITableViewDataSource, AjouterUnMotDelegate {

    
    var mots : [Mot] = []
    let reuseIdentifier = "motCell"
    var list : List?
    
    lazy var motsTableView : UITableView = {
        var tv = UITableView()
        tv.delegate = self
        tv.dataSource = self
        tv.backgroundColor = .clear
        tv.separatorStyle = .none
        tv.translatesAutoresizingMaskIntoConstraints = false
        return tv
    }()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationItem.setLeftBarButton(UIBarButtonItem(title: "Revenir", style: .plain, target: self, action: #selector(handleRevenir)), animated: true)
        self.navigationItem.setRightBarButton(UIBarButtonItem(title: "Ajouter", style: .plain, target: self, action: #selector(handleAjouter)), animated: true)
        self.motsTableView.register(VCMotCell.self, forCellReuseIdentifier: reuseIdentifier)
        self.view.backgroundColor = .white
        loadWords()
        setupViews()
    }
    
    func loadWords() {
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
    }
    
    func handleAjouter() {
        let controller = AjouterMotViewController()
        controller.delegateMot = self
        controller.liste = list
        controller.navigationItem.title = self.navigationItem.title
        let navController = UINavigationController(rootViewController: controller)
        present(navController, animated: true, completion: nil)
    }
    
    func envoyerMot(mot: Mot) {
        do {
            let fileURL = try! FileManager.default.url(for: .documentDirectory, in: .userDomainMask, appropriateFor: nil, create: false)
                .appendingPathComponent("Vocs.sqlite")
            let db = try Connection("\(fileURL)")
            let french = Expression<String>("french")
            let english = Expression<String>("english")
            let word_id = Expression<Int>("id_word")
            let list_id = Expression<Int>("id_list")
            let words = Table("words")
            let insert = words.insert(french <- mot.french!,english <- mot.english!)
            var rowid = try db.run(insert)
            mot.id = Int(rowid)
            let words_lists = Table("words_lists")
            rowid = try db.run(words_lists.insert(word_id <- Int(rowid), list_id <- (self.list?.id_list)!))
            
            self.mots.append(mot)
        }   catch {
            print(error)
            return
        }
        
        let indexPath = IndexPath(row: mots.count - 1, section: 0)
        _ = Timer.scheduledTimer(timeInterval: 0.3, target: self, selector: #selector(insertRow), userInfo: indexPath, repeats: false)
    }
    
    func setupViews(){
        self.view.addSubview(motsTableView)
        
        motsTableView.topAnchor.constraint(equalTo: view.topAnchor, constant : 10).isActive = true
        motsTableView.centerXAnchor.constraint(equalTo: self.view.centerXAnchor).isActive = true
        motsTableView.widthAnchor.constraint(equalTo: self.view.widthAnchor, multiplier: 9/10).isActive = true
        motsTableView.heightAnchor.constraint(equalTo: self.view.heightAnchor).isActive = true
    }
    
    func handleRevenir() {
        self.navigationController?.popViewController(animated: true)
    }
    
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.mots.count
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 45
    }
    
    func insertRow(timer : Timer) {
        motsTableView.insertRows(at: [timer.userInfo as! IndexPath], with: .automatic)
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cell:VCMotCell = tableView.dequeueReusableCell(withIdentifier: reuseIdentifier)! as! VCMotCell
        cell.setText(text: mots[indexPath.row].french! + " - " + mots[indexPath.row].english! )
        
        return cell
        
    }
    
    func deleteMot(indexPath : IndexPath) {
        do {
            let fileURL = try! FileManager.default.url(for: .documentDirectory, in: .userDomainMask, appropriateFor: nil, create: false)
                .appendingPathComponent("Vocs.sqlite")
            let db = try Connection("\(fileURL)")
            let word_id = Expression<Int>("id_word")
            let words = Table("words")
            let words_lists = Table("words_lists")
            if let id_word = mots[indexPath.row].id {
                let word_filtered = words.filter(word_id == id_word)
                try db.run(word_filtered.delete())
                let words_lists_filtered = words_lists.filter(word_id == id_word)
                try db.run(words_lists_filtered.delete())
            }
        }   catch {
            print(error)
            return
        }
        mots.remove(at: indexPath.row)
    }
    
    func tableView(_ tableView: UITableView, titleForDeleteConfirmationButtonForRowAt indexPath: IndexPath) -> String? {
        return "Supprimer"
    }
    
    func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCellEditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == UITableViewCellEditingStyle.delete {
            deleteMot(indexPath: indexPath)
            tableView.deleteRows(at: [indexPath], with: UITableViewRowAnimation.fade)
        }
    }
}
