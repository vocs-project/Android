//
//  ChooseListViewController.swift
//  Vocs
//
//  Created by Mathis Delaunay on 27/05/2017.
//  Copyright © 2017 Wathis. All rights reserved.
//

import UIKit
import SQLite

class ChoisirListeViewController: UIViewController, UITableViewDataSource,UITableViewDelegate {
    
    let reuseIdentifier = "listeCell"
    var lists : [List] = []
    var dejaCharge = false
    
    let headerTableView = VCHeaderListeWithoutButton(text: "Choisir une liste")
    var labelIndispobible = VCLabelMenu(text: "Vous n'avez aucune liste",size: 20)
    
    lazy var listesTableView : UITableView = {
        var tv = UITableView()
        tv.delegate = self
        tv.dataSource = self
        tv.backgroundColor = .clear
        tv.separatorStyle = .none
        tv.translatesAutoresizingMaskIntoConstraints = false
        return tv
    }()
    
    override func viewWillAppear(_ animated: Bool) {
        chargerLesListes()
        listesTableView.reloadData()
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationItem.title = "Traduction"
        self.view.backgroundColor = .white
        chargerLesListes()
        listesTableView.register(VCListeCell.self, forCellReuseIdentifier: reuseIdentifier)
        setupViews()
        checkVide()
    }
    
    func checkVide() {
        if (self.lists.isEmpty){
            messageVide()
        }
    }
    
    func chargerLesListes() {
        lists.removeAll()
        do {
            let fileURL = try! FileManager.default.url(for: .documentDirectory, in: .userDomainMask, appropriateFor: nil, create: false)
                .appendingPathComponent("Vocs.sqlite")
            let db = try Connection("\(fileURL)")
            print(fileURL)
            print("Connecté")
            let idList = Expression<Int>("id_list")
            let nameList = Expression<String>("name")
            let words = Table("lists")
            for list in try db.prepare(words) {
                lists.append(List(id_list: list[idList],name: list[nameList]))
            }
        }   catch {
            print("Erreur")
            return
        }
    }
    
    func setupViews() {
        
        self.view.addSubview(headerTableView)
        
        headerTableView.topAnchor.constraint(equalTo: self.view.topAnchor, constant : 10).isActive = true
        headerTableView.centerXAnchor.constraint(equalTo: self.view.centerXAnchor).isActive = true
        headerTableView.heightAnchor.constraint(equalToConstant: 40).isActive = true
        headerTableView.widthAnchor.constraint(equalTo: self.view.widthAnchor, multiplier: 9/10).isActive = true
        
        
        self.view.addSubview(listesTableView)
        
        listesTableView.topAnchor.constraint(equalTo: headerTableView.bottomAnchor, constant : 10).isActive = true
        listesTableView.centerXAnchor.constraint(equalTo: self.view.centerXAnchor).isActive = true
        listesTableView.widthAnchor.constraint(equalTo: self.view.widthAnchor, multiplier: 9/10).isActive = true
        listesTableView.heightAnchor.constraint(equalTo: self.view.heightAnchor).isActive = true
    }
    
    func messageVide() {
        self.view.addSubview(labelIndispobible)
        labelIndispobible.centerYAnchor.constraint(equalTo: view.centerYAnchor,constant : -30).isActive = true
        labelIndispobible.widthAnchor.constraint(equalTo: self.view.widthAnchor).isActive = true
        labelIndispobible.heightAnchor.constraint(equalToConstant: 50).isActive = true
        labelIndispobible.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
    }
    
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.lists.count
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 45
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cell:VCListeCell = tableView.dequeueReusableCell(withIdentifier: reuseIdentifier)! as! VCListeCell
        cell.setText(text: lists[indexPath.row].name!)
        
        return cell
        
    }
    func insertRow(timer : Timer) {
        listesTableView.insertRows(at: [timer.userInfo as! IndexPath], with: .automatic)
    }
    
    
    func listeEstVide(indexPath : IndexPath) -> Bool {
        do {
            let fileURL = try! FileManager.default.url(for: .documentDirectory, in: .userDomainMask, appropriateFor: nil, create: false)
                .appendingPathComponent("Vocs.sqlite")
            let db = try Connection("\(fileURL)")
            let count = try db.scalar("SELECT count(*) FROM words_lists where id_list = \(lists[indexPath.row].id_list!);" ) as! Int64
            if (count == 0){
                return true
            } else {
                return false
            }
        } catch {
            print(error)
        }
        return true
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if (!listeEstVide(indexPath: indexPath)){
            let controller = TraductionViewController()
            controller.list =  lists[indexPath.row]
            controller.modalTransitionStyle = UIModalTransitionStyle.flipHorizontal
            let navController = UINavigationController(rootViewController: controller)
            present(navController, animated: true, completion: nil)
        } else {
            let alertController = UIAlertController(title: "Problème de liste", message:
                "\(lists[indexPath.row].name!) est vide", preferredStyle: UIAlertControllerStyle.alert)
            let cancelAction = UIAlertAction(title: "Retour", style: UIAlertActionStyle.cancel) {
                UIAlertAction in
                print("Appuyé sur retour")
            }
            alertController.addAction(cancelAction)
            self.present(alertController, animated: true, completion: nil)
        }
    }
}
