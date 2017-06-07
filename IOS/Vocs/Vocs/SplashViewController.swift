//
//  SplashViewController.swift
//  Vocs
//
//  Created by Mathis Delaunay on 02/06/2017.
//  Copyright © 2017 Wathis. All rights reserved.
//

import UIKit

class SplashViewController: UIViewController {

    @IBOutlet weak var loadBar: UIProgressView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        if #available(iOS 10.0, *) {
            _ = Timer.scheduledTimer(withTimeInterval: 0.2, repeats: true, block: { (TheTimer) in
                if self.loadBar.progress < 0.9 {
                    self.loadBar.setProgress(self.loadBar.progress + 0.2, animated: true)
                } else {
                    TheTimer.invalidate()
                    _ = Timer.scheduledTimer(timeInterval: 0.3, target: self, selector: #selector(self.goToVocs), userInfo: nil, repeats: false)
                }
            })
        } else {
            _ = Timer.scheduledTimer(timeInterval: 1, target: self, selector: #selector(goToVocs), userInfo: nil, repeats: false)
        }
    }
    
    func goToVocs() {
        let controller = TabBarController()
        controller.modalTransitionStyle = UIModalTransitionStyle.flipHorizontal
        present(controller, animated: true, completion: nil)
    }

    
}
