//
//  Mot.swift
//  Vocs
//
//  Created by Mathis Delaunay on 27/05/2017.
//  Copyright © 2017 Wathis. All rights reserved.
//

import Foundation

class Mot  {
    var french : String?
    var english : String?
    var id : Int?
    
    init(id : Int, french : String, english : String) {
        self.french = french
        self.english = english
        self.id = id;
    }
    init(french : String, english : String) {
        self.french = french
        self.english = english
    }
}
