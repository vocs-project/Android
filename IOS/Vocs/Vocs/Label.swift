//
//  VCLabelMenu.swift
//  Vocs
//
//  Created by Mathis Delaunay on 13/05/2017.
//  Copyright © 2017 Wathis. All rights reserved.
//

import UIKit

class VCLabelMenu: UILabel {
    
    init(text : String, size : Int) {
        super.init(frame: CGRect(x: 0, y: 0, width: 0, height: 0))
        self.translatesAutoresizingMaskIntoConstraints = false
        self.font = UIFont(name: "HelveticaNeue", size: CGFloat(size))
        self.text = text
        self.textAlignment = .center
        self.textColor = UIColor(rgb: 0x4A4A4A)
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
}
class VCLabelMot : UILabel {
    
    init(text : String) {
        super.init(frame: CGRect(x: 0, y: 0, width: 0, height: 0))
        self.translatesAutoresizingMaskIntoConstraints = false
        self.font = UIFont(name: "HelveticaNeue-Bold", size: 25)
        self.text = text
        self.textAlignment = .center
        self.textColor = UIColor(rgb: 0x696969)
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}

class VCLabelCopyright : UILabel {
    
    init() {
        super.init(frame: CGRect(x: 0, y: 0, width: 0, height: 0))
        self.font = UIFont(name:  "Helvetica Neue", size: 15)
        self.text = "@Copyright Vocs 2017"
        self.textAlignment = .center
        self.translatesAutoresizingMaskIntoConstraints = false
        self.textColor = .black
        
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}
