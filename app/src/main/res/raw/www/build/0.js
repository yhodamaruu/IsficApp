webpackJsonp([0],{

/***/ 297:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AddPageModule", function() { return AddPageModule; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_ionic_angular__ = __webpack_require__(29);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__add__ = __webpack_require__(298);
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};



var AddPageModule = /** @class */ (function () {
    function AddPageModule() {
    }
    AddPageModule = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["I" /* NgModule */])({
            declarations: [
                __WEBPACK_IMPORTED_MODULE_2__add__["a" /* AddPage */],
            ],
            imports: [
                __WEBPACK_IMPORTED_MODULE_1_ionic_angular__["e" /* IonicPageModule */].forChild(__WEBPACK_IMPORTED_MODULE_2__add__["a" /* AddPage */]),
            ],
        })
    ], AddPageModule);
    return AddPageModule;
}());

//# sourceMappingURL=add.module.js.map

/***/ }),

/***/ 298:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AddPage; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_ionic_angular__ = __webpack_require__(29);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_forms__ = __webpack_require__(15);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__providers_image_image__ = __webpack_require__(208);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__providers_database_database__ = __webpack_require__(103);
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};





/**
 * Generated class for the AddPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */
var AddPage = /** @class */ (function () {
    function AddPage(navCtrl, NP, fb, IMAGE, DB, toastCtrl) {
        this.navCtrl = navCtrl;
        this.NP = NP;
        this.fb = fb;
        this.IMAGE = IMAGE;
        this.DB = DB;
        this.toastCtrl = toastCtrl;
        this.isEdited = false;
        this.hideForm = false;
        this.form = this.fb.group({
            "nom": ["", __WEBPACK_IMPORTED_MODULE_2__angular_forms__["f" /* Validators */].required],
            "prenom": ["", __WEBPACK_IMPORTED_MODULE_2__angular_forms__["f" /* Validators */].required],
            "age": ["", !__WEBPACK_IMPORTED_MODULE_2__angular_forms__["f" /* Validators */].required],
            "sexe": ["", !__WEBPACK_IMPORTED_MODULE_2__angular_forms__["f" /* Validators */].required],
            "bac": ["", !__WEBPACK_IMPORTED_MODULE_2__angular_forms__["f" /* Validators */].required],
            "lycee": ["", !__WEBPACK_IMPORTED_MODULE_2__angular_forms__["f" /* Validators */].required],
            "image": ["", !__WEBPACK_IMPORTED_MODULE_2__angular_forms__["f" /* Validators */].required],
            "quartier": ["", !__WEBPACK_IMPORTED_MODULE_2__angular_forms__["f" /* Validators */].required],
            "contact": ["", __WEBPACK_IMPORTED_MODULE_2__angular_forms__["f" /* Validators */].required],
            "nomParent": ["", !__WEBPACK_IMPORTED_MODULE_2__angular_forms__["f" /* Validators */].required],
            "contactParent": ["", !__WEBPACK_IMPORTED_MODULE_2__angular_forms__["f" /* Validators */].required]
        });
        this.resetFields();
        if (this.NP.get("key") && this.NP.get("rev")) {
            this.recordId = this.NP.get("key");
            this.revisionId = this.NP.get("rev");
            this.isEdited = true;
            this.selectStudent(this.recordId);
            this.pageTitle = 'Amend entry';
        }
        else {
            this.recordId = '';
            this.revisionId = '';
            this.isEdited = false;
            this.pageTitle = 'Create entry';
        }
    }
    AddPage.prototype.ionViewDidLoad = function () {
        console.log('ionViewDidLoad AddPage');
    };
    AddPage.prototype.selectStudent = function (id) {
        var _this = this;
        this.DB.retrieveStudent(id)
            .then(function (doc) {
            _this.studentNom = doc[0].nom;
            _this.studentPrenom = doc[0].prenom;
            _this.studentAge = doc[0].age;
            _this.studentSexe = doc[0].sexe;
            _this.studentQuartier = doc[0].quartier;
            _this.studentContact = doc[0].contact;
            _this.studentNomParent = doc[0].nomParent;
            _this.studentContactParent = doc[0].contactParent;
            _this.studentBac = doc[0].bac;
            _this.studentLycee = doc[0].lycee;
            _this.studentImage = doc[0].image;
            _this.characterImage = doc[0].image;
            _this.recordId = doc[0].id;
            _this.revisionId = doc[0].rev;
        });
    };
    AddPage.prototype.saveStudent = function () {
        var _this = this;
        var image;
        if (this.form.controls["image"].value === "") {
            image = "assets/imgs/logo.png";
        }
        else {
            image = this.form.controls["image"].value;
        }
        var nom = this.form.controls["nom"].value, prenom = this.form.controls["prenom"].value, age = this.form.controls["age"].value, sexe = this.form.controls["sexe"].value, quartier = this.form.controls["quartier"].value, contact = this.form.controls["contact"].value, nomParent = this.form.controls["nomParent"].value, contactParent = this.form.controls["contactParent"].value, bac = this.form.controls["bac"].value, lycee = this.form.controls["lycee"].value, 
        //image: string = this.form.controls["image"].value,
        revision = this.revisionId, id = this.recordId;
        if (this.recordId !== '') {
            this.DB.updateStudent(id, nom, prenom, age, quartier, sexe, contact, bac, lycee, nomParent, contactParent, image, revision)
                .then(function (data) {
                _this.hideForm = true;
                _this.sendNotification(nom + " " + prenom + " a \u00E9t\u00E9 mise \u00E0 jour avec succ\u00E8s");
            });
        }
        else {
            this.DB.addStudent(nom, prenom, age, quartier, sexe, contact, bac, lycee, nomParent, contactParent, image)
                .then(function (data) {
                _this.hideForm = true;
                _this.resetFields();
                _this.sendNotification(nom + " " + prenom + " a \u00E9t\u00E9 ajout\u00E9 \u00E0 la liste");
            });
        }
    };
    AddPage.prototype.takePhotograph = function () {
        var _this = this;
        this.IMAGE.takePhotograph()
            .then(function (image) {
            _this.characterImage = image.toString();
            _this.studentImage = image.toString();
        })
            .catch(function (err) {
            console.log(err);
        });
    };
    AddPage.prototype.selectImage = function () {
        var _this = this;
        this.IMAGE.selectPhotograph()
            .then(function (image) {
            _this.characterImage = image.toString();
            _this.studentImage = image.toString();
        })
            .catch(function (err) {
            console.log(err);
        });
    };
    AddPage.prototype.deleteStudent = function () {
        var _this = this;
        var nom, prenom;
        this.DB.retrieveStudent(this.recordId)
            .then(function (doc) {
            nom = doc[0].nom;
            prenom = doc[0].prenom;
            return _this.DB.removeStudent(_this.recordId, _this.revisionId);
        })
            .then(function (data) {
            _this.hideForm = true;
            _this.sendNotification(nom + " " + prenom + " a \u00E9t\u00E9 supprim\u00E9 de la liste avec succ\u00E8s");
        })
            .catch(function (err) {
            console.log(err);
        });
    };
    AddPage.prototype.resetFields = function () {
        this.studentNom = "";
        this.studentPrenom = "";
        this.studentAge = "";
        this.studentQuartier = "";
        this.studentContact = "";
        this.studentNomParent = "";
        this.studentContactParent = "";
        this.studentLycee = "";
        this.studentImage = "";
        this.characterImage = "";
    };
    AddPage.prototype.sendNotification = function (message) {
        var notification = this.toastCtrl.create({
            message: message,
            duration: 3000
        });
        notification.present();
    };
    AddPage = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["m" /* Component */])({
            selector: 'page-add',template:/*ion-inline-start:"/home/dsarrus/project/studentcollect/src/pages/add/add.html"*/'<!--\n  Generated template for the AddPage page.\n\n  See http://ionicframework.com/docs/components/#navigation for more info on\n  Ionic pages and navigation.\n-->\n<ion-header>\n  <ion-navbar>\n    <ion-title>{{ pageTitle }}</ion-title>\n  </ion-navbar>\n</ion-header>\n\n\n<ion-content padding>\n\n  <div>\n    <ion-item *ngIf="isEdited && !hideForm">\n      <button ion-button item-right color="secondary" text-center block (click)="deleteStudent()">Remove this Entry?</button>\n    </ion-item>\n\n\n    <div *ngIf="hideForm">\n      <ion-item class="post-entry-message" text-wrap>\n        <h2>Succès!</h2>\n        <p>Vous souhaitez peut-être modifier une entrée existante ou ajouter un nouvel enregistrement?</p>\n        <p>Retournez simplement à la page d\'accueil et sélectionnez l\'option que vous souhaitez poursuivre.</p>\n      </ion-item>\n    </div>\n\n\n\n    <div *ngIf="!hideForm">\n      <form [formGroup]="form" (ngSubmit)="saveStudent()">\n\n\n        <ion-item-group>\n          <ion-item-divider color="light">Nom</ion-item-divider>\n          <ion-item>\n            <ion-input type="text" placeholder="Entrer un nom..." formControlName="nom" [(ngModel)]="studentNom"></ion-input>\n          </ion-item>\n        </ion-item-group>\n\n        <ion-item-group>\n          <ion-item-divider color="light">Prénom</ion-item-divider>\n          <ion-item>\n            <ion-input type="text" placeholder="Entrer un prénom..." formControlName="prenom" [(ngModel)]="studentPrenom"></ion-input>\n          </ion-item>\n        </ion-item-group>\n\n        <ion-item-group>\n          <ion-item-divider color="light">Âge</ion-item-divider>\n          <ion-item>\n            <ion-input type="number" placeholder="Entrer un âge..." formControlName="age" [(ngModel)]="studentAge"></ion-input>\n          </ion-item>\n        </ion-item-group>\n\n        <ion-list>\n          <ion-item-group>\n            <ion-item-divider color="light">Sexe</ion-item-divider>\n            <ion-item>\n              <ion-label>Selectionner: </ion-label>\n              <ion-select class="select" interface="action-sheet" formControlName="sexe" block [(ngModel)]="studentSexe">\n                <ion-option value="Masculin">Masculin</ion-option>\n                <ion-option value="Féminin">Féminin</ion-option>\n              </ion-select>\n            </ion-item>\n          </ion-item-group>\n\n\n          <ion-item-group>\n            <ion-item-divider color="light">Quartier</ion-item-divider>\n            <ion-item>\n              <ion-input type="text" placeholder="Entrer un quartier..." formControlName="quartier" [(ngModel)]="studentQuartier"></ion-input>\n            </ion-item>\n          </ion-item-group>\n\n          <ion-item-group>\n            <ion-item-divider color="light">Contact</ion-item-divider>\n            <ion-item>\n              <ion-input type="text" placeholder="Entrer un contact..." formControlName="contact" [(ngModel)]="studentContact"></ion-input>\n            </ion-item>\n          </ion-item-group>\n\n          <ion-item-group>\n            <ion-item-divider color="light">Nom parent</ion-item-divider>\n            <ion-item>\n              <ion-input type="text" placeholder="Entrer le nom du parent..." formControlName="nomParent" [(ngModel)]="studentNomParent"></ion-input>\n            </ion-item>\n          </ion-item-group>\n\n          <ion-item-group>\n            <ion-item-divider color="light">Contact Parent</ion-item-divider>\n            <ion-item>\n              <ion-input type="text" placeholder="Entrer un contact du parent..." formControlName="contactParent" [(ngModel)]="studentContactParent"></ion-input>\n            </ion-item>\n          </ion-item-group>\n\n          <ion-item-group>\n            <ion-item-divider color="light">Baccalaureat</ion-item-divider>\n            <ion-item>\n              <ion-label>Selectionner: </ion-label>\n              <ion-select class="select" interface="action-sheet" formControlName="bac" block [(ngModel)]="studentBac">\n                <ion-option value="Non">Non</ion-option>\n                <ion-option value="Oui">Oui</ion-option>\n              </ion-select>\n            </ion-item>\n          </ion-item-group>\n\n          <ion-item-group>\n            <ion-item-divider color="light">Lycée</ion-item-divider>\n            <ion-item>\n              <ion-input type="text" placeholder="Entrer le nom du lycée..." formControlName="lycee" [(ngModel)]="studentLycee"></ion-input>\n            </ion-item>\n          </ion-item-group>\n\n\n          <ion-item-group>\n            <ion-item-divider color="light">Image ou photo</ion-item-divider>\n            <ion-item>\n              <a ion-button block margin-bottom color="primary" (click)="takePhotograph()">\n                Prendre une photo\n              </a>\n            </ion-item>\n\n            <ion-item>\n              <a ion-button block margin-bottom color="secondary" (click)="selectImage()">\n                Selectionner une image existante\n              </a>\n            </ion-item>\n\n            <ion-item>\n              <img [src]="characterImage">\n              <input type="hidden" name="image" formControlName="image" [(ngModel)]="studentImage">\n            </ion-item>\n          </ion-item-group>\n\n\n          <ion-item>\n            <button ion-button color="primary" text-center block [disabled]="!form.valid">Valider</button>\n          </ion-item>\n\n\n\n        </ion-list>\n\n\n\n      </form>\n    </div>\n  </div>\n\n\n</ion-content>'/*ion-inline-end:"/home/dsarrus/project/studentcollect/src/pages/add/add.html"*/,
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1_ionic_angular__["f" /* NavController */], __WEBPACK_IMPORTED_MODULE_1_ionic_angular__["g" /* NavParams */],
            __WEBPACK_IMPORTED_MODULE_2__angular_forms__["a" /* FormBuilder */],
            __WEBPACK_IMPORTED_MODULE_3__providers_image_image__["a" /* ImageProvider */],
            __WEBPACK_IMPORTED_MODULE_4__providers_database_database__["a" /* DatabaseProvider */],
            __WEBPACK_IMPORTED_MODULE_1_ionic_angular__["i" /* ToastController */]])
    ], AddPage);
    return AddPage;
}());

//# sourceMappingURL=add.js.map

/***/ })

});
//# sourceMappingURL=0.js.map