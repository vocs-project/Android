//
//  AppDelegate.swift
//  Vocs
//
//  Created by Mathis Delaunay on 04/05/2017.
//  Copyright © 2017 Wathis. All rights reserved.
//

import SQLite
import UIKit

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {

    var window: UIWindow?
 

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplicationLaunchOptionsKey: Any]?) -> Bool {
        window = UIWindow(frame: UIScreen.main.bounds)
        window?.makeKeyAndVisible()
        let navigationBarAppearace = UINavigationBar.appearance()
        navigationBarAppearace.setBackgroundImage(#imageLiteral(resourceName: "NavigationBackground").resizableImage(withCapInsets: UIEdgeInsets.zero, resizingMode: .stretch), for: .default)
        navigationBarAppearace.isTranslucent = false
        navigationBarAppearace.tintColor = .white
        navigationBarAppearace.barTintColor = UIColor(r: 40, g: 125, b: 192)
        navigationBarAppearace.titleTextAttributes = [NSForegroundColorAttributeName : UIColor.white, NSFontAttributeName : UIFont(name: "HelveticaNeue", size: 20) as Any]
        //GENERAL SETUPS
        //Mettre   -->   View controller-based status bar appearance      a   NO
        UIApplication.shared.statusBarStyle = .lightContent
        UITabBar.appearance().tintColor = UIColor(r: 40, g: 125, b: 192)
        
        //Modify KeyBoard color
        UITextField.appearance().keyboardAppearance = .dark
        findIfSqliteDBExists()
        window?.rootViewController = SplashViewController()
        return true
    }
    
    func findIfSqliteDBExists(){
        let docsDir     : URL       = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask).first!
        let dbPath      : URL       = docsDir.appendingPathComponent("Vocs.sqlite")
        let strDBPath   : String    = dbPath.path
        let fileManager : FileManager   = FileManager.default
        if !(fileManager.fileExists(atPath:strDBPath)){
            createTables()
        }
    }
    
    func createTables() {
        let query = "create table words" +
            "(" +
            "id_word integer ," +
            "french varchar(255)," +
            "english varchar(255)," +
            "primary key (id_word)" +
            ");" +
            
            "create table lists" +
            "(" +
            "id_list integer ," +
            "name varchar(255)," +
            "primary key (id_list)" +
            ");" +
            
            "create table words_lists" +
            
            "(" +
            "id_word integer," +
            "id_list integer," +
            "primary key (id_list,id_word)," +
            "foreign key (id_word) references words," +
            "foreign key (id_list) references lists" +
        ");"
        do {
            let fileURL = try! FileManager.default.url(for: .documentDirectory, in: .userDomainMask, appropriateFor: nil, create: false)
                .appendingPathComponent("Vocs.sqlite")
            let db = try Connection("\(fileURL)")
            try db.execute(query)
        } catch {
            print(error)
            return
        }
    }

    func applicationWillResignActive(_ application: UIApplication) {
        // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
        // Use this method to pause ongoing tasks, disable timers, and invalidate graphics rendering callbacks. Games should use this method to pause the game.
    }

    func applicationDidEnterBackground(_ application: UIApplication) {
        // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later.
        // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
    }

    func applicationWillEnterForeground(_ application: UIApplication) {
        // Called as part of the transition from the background to the active state; here you can undo many of the changes made on entering the background.
    }

    func applicationDidBecomeActive(_ application: UIApplication) {
        // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
    }

    func applicationWillTerminate(_ application: UIApplication) {
        // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
    }


}

