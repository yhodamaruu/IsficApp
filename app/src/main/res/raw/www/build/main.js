webpackJsonp([1],{

/***/ 103:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return DatabaseProvider; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_common_http__ = __webpack_require__(57);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_map__ = __webpack_require__(156);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_map___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_map__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_pouchdb__ = __webpack_require__(268);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_ionic_angular__ = __webpack_require__(29);
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};





/*
  Generated class for the DatabaseProvider provider.

  See https://angular.io/guide/dependency-injection for more info on providers
  and Angular DI.
*/
var DatabaseProvider = /** @class */ (function () {
    function DatabaseProvider(http, alertCtrl) {
        this.http = http;
        this.alertCtrl = alertCtrl;
        this.success = true;
        console.log('Hello DatabaseProvider Provider');
        this.initialiseDB();
    }
    DatabaseProvider.prototype.initialiseDB = function () {
        this._DB = new __WEBPACK_IMPORTED_MODULE_3_pouchdb__["a" /* default */]('Students');
    };
    DatabaseProvider.prototype.addStudent = function (nom, prenom, age, quartier, sexe, contact, bac, lycee, nomParent, contactParent, image) {
        var _this = this;
        var timeStamp = new Date().toISOString(), base64String = image.substring(23), Student = {
            _id: timeStamp,
            nom: nom,
            prenom: prenom,
            age: age,
            quartier: quartier,
            sexe: sexe,
            contact: contact,
            bac: bac,
            lycee: lycee,
            nomParent: nomParent,
            contactParent: contactParent,
            _attachments: {
                "character.jpg": {
                    content_type: 'image/jpeg',
                    data: base64String
                }
            }
        };
        return new Promise(function (resolve) {
            _this._DB.put(Student).catch(function (err) {
                _this.success = false;
            });
            resolve(true);
        });
    };
    DatabaseProvider.prototype.updateStudent = function (id, nom, prenom, age, quartier, sexe, contact, bac, lycee, nomParent, contactParent, image, revision) {
        var _this = this;
        var base64String = image.substring(23), Student = {
            _id: id,
            _rev: revision,
            nom: nom,
            prenom: prenom,
            age: age,
            quartier: quartier,
            sexe: sexe,
            contact: contact,
            bac: bac,
            lycee: lycee,
            nomParent: nomParent,
            contactParent: contactParent,
            _attachments: {
                "character.jpg": {
                    content_type: 'image/jpeg',
                    data: base64String
                }
            }
        };
        return new Promise(function (resolve) {
            _this._DB.put(Student)
                .catch(function (err) {
                _this.success = false;
            });
            if (_this.success) {
                resolve(true);
            }
        });
    };
    DatabaseProvider.prototype.retrieveStudent = function (id) {
        var _this = this;
        return new Promise(function (resolve) {
            _this._DB.get(id, { attachments: true })
                .then(function (doc) {
                var item = [], dataURIPrefix = 'data:image/jpeg;base64,', attachment;
                if (doc._attachments) {
                    attachment = doc._attachments["character.jpg"].data;
                }
                item.push({
                    id: id,
                    rev: doc._rev,
                    nom: doc.nom,
                    prenom: doc.prenom,
                    age: doc.age,
                    quartier: doc.quartier,
                    sexe: doc.sexe,
                    contact: doc.contact,
                    bac: doc.bac,
                    lycee: doc.lycee,
                    nomParent: doc.nomParent,
                    contactParent: doc.contactParent,
                    image: dataURIPrefix + attachment
                });
                resolve(item);
            });
        });
    };
    DatabaseProvider.prototype.retrieveStudents = function () {
        var _this = this;
        return new Promise(function (resolve) {
            _this._DB.allDocs({ include_docs: true, descending: true, attachments: true }, function (err, doc) {
                var k, items = [], row = doc.rows;
                for (k in row) {
                    var item = row[k].doc, dataURIPrefix = 'data:image/jpeg;base64,', attachment;
                    if (item._attachments) {
                        attachment = dataURIPrefix + item._attachments["character.jpg"].data;
                    }
                    items.push({
                        id: item._id,
                        rev: item._rev,
                        nom: item.nom,
                        prenom: item.prenom,
                        age: item.age,
                        quartier: item.quartier,
                        sexe: item.sexe,
                        contact: item.contact,
                        bac: item.bac,
                        lycee: item.lycee,
                        nomParent: item.nomParent,
                        contactParent: item.contactParent,
                        image: attachment
                    });
                }
                resolve(items);
            });
        });
    };
    DatabaseProvider.prototype.removeStudent = function (id, rev) {
        var _this = this;
        return new Promise(function (resolve) {
            var Student = { _id: id, _rev: rev };
            _this._DB.remove(Student)
                .catch(function (err) {
                _this.success = false;
            });
            if (_this.success) {
                resolve(true);
            }
        });
    };
    DatabaseProvider.prototype.errorHandler = function (err) {
        var headsUp = this.alertCtrl.create({
            title: 'Heads Up!',
            subTitle: err,
            buttons: ['Got It!']
        });
        headsUp.present();
    };
    DatabaseProvider = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_1__angular_core__["A" /* Injectable */])(),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_0__angular_common_http__["a" /* HttpClient */],
            __WEBPACK_IMPORTED_MODULE_4_ionic_angular__["a" /* AlertController */]])
    ], DatabaseProvider);
    return DatabaseProvider;
}());

//# sourceMappingURL=database.js.map

/***/ }),

/***/ 114:
/***/ (function(module, exports) {

function webpackEmptyAsyncContext(req) {
	// Here Promise.resolve().then() is used instead of new Promise() to prevent
	// uncatched exception popping up in devtools
	return Promise.resolve().then(function() {
		throw new Error("Cannot find module '" + req + "'.");
	});
}
webpackEmptyAsyncContext.keys = function() { return []; };
webpackEmptyAsyncContext.resolve = webpackEmptyAsyncContext;
module.exports = webpackEmptyAsyncContext;
webpackEmptyAsyncContext.id = 114;

/***/ }),

/***/ 155:
/***/ (function(module, exports, __webpack_require__) {

var map = {
	"../pages/add/add.module": [
		297,
		0
	]
};
function webpackAsyncContext(req) {
	var ids = map[req];
	if(!ids)
		return Promise.reject(new Error("Cannot find module '" + req + "'."));
	return __webpack_require__.e(ids[1]).then(function() {
		return __webpack_require__(ids[0]);
	});
};
webpackAsyncContext.keys = function webpackAsyncContextKeys() {
	return Object.keys(map);
};
webpackAsyncContext.id = 155;
module.exports = webpackAsyncContext;

/***/ }),

/***/ 202:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return TabsPage; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__about_about__ = __webpack_require__(203);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__contact_contact__ = __webpack_require__(204);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__home_home__ = __webpack_require__(205);
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




var TabsPage = /** @class */ (function () {
    function TabsPage() {
        this.tab1Root = __WEBPACK_IMPORTED_MODULE_3__home_home__["a" /* HomePage */];
        this.tab2Root = __WEBPACK_IMPORTED_MODULE_1__about_about__["a" /* AboutPage */];
        this.tab3Root = __WEBPACK_IMPORTED_MODULE_2__contact_contact__["a" /* ContactPage */];
    }
    TabsPage = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["m" /* Component */])({template:/*ion-inline-start:"/home/dsarrus/project/studentcollect/src/pages/tabs/tabs.html"*/'<ion-tabs>\n  <ion-tab [root]="tab1Root" tabTitle="Accueil" tabIcon="home"></ion-tab>\n  <ion-tab [root]="tab2Root" tabTitle="A propos" tabIcon="information-circle"></ion-tab>\n  <ion-tab [root]="tab3Root" tabTitle="Contact" tabIcon="contacts"></ion-tab>\n</ion-tabs>\n'/*ion-inline-end:"/home/dsarrus/project/studentcollect/src/pages/tabs/tabs.html"*/
        }),
        __metadata("design:paramtypes", [])
    ], TabsPage);
    return TabsPage;
}());

//# sourceMappingURL=tabs.js.map

/***/ }),

/***/ 203:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AboutPage; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_ionic_angular__ = __webpack_require__(29);
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var AboutPage = /** @class */ (function () {
    function AboutPage(navCtrl) {
        this.navCtrl = navCtrl;
    }
    AboutPage = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["m" /* Component */])({
            selector: 'page-about',template:/*ion-inline-start:"/home/dsarrus/project/studentcollect/src/pages/about/about.html"*/'<ion-header>\n  <ion-navbar>\n    <ion-title>\n      A propos\n    </ion-title>\n  </ion-navbar>\n</ion-header>\n\n<ion-content padding>\n  <div>\n    <ion-item class="post-entry-message" text-wrap>\n      <h1>STUDENTCOLLECT</h1>\n      <p><b>STUDENTCOLLECT</b> est une application qui permet de saisir des informations grâce à un formulaire.</p>\n      <p>A l\'accueil, on a la liste des enregistrements effectués.</p>\n      <ul>\n        <li>Le bouton "NOUVEAU" permet de procéder à un nouvel enregistrement.</li>\n        <li>Le bouton "ENVOI" permet d\'envoyer un EMAIL contenant un fichier joint nommé "studentDATA.csv" représentant\n          tous les données\n          enregistrées dans l\'application. </li>\n      </ul>\n    </ion-item>\n  </div>\n  <div>\n    <ion-item>\n      <ion-thumbnail item-left>\n        <img src="assets/imgs/asinfo.png" />\n      </ion-thumbnail>\n      <a href="http://asinfo-ml.com"><i>By ASInfo</i></a>\n    </ion-item>\n  </div>\n\n</ion-content>'/*ion-inline-end:"/home/dsarrus/project/studentcollect/src/pages/about/about.html"*/
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1_ionic_angular__["f" /* NavController */]])
    ], AboutPage);
    return AboutPage;
}());

//# sourceMappingURL=about.js.map

/***/ }),

/***/ 204:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ContactPage; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_ionic_angular__ = __webpack_require__(29);
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var ContactPage = /** @class */ (function () {
    function ContactPage(navCtrl) {
        this.navCtrl = navCtrl;
    }
    ContactPage = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["m" /* Component */])({
            selector: 'page-contact',template:/*ion-inline-start:"/home/dsarrus/project/studentcollect/src/pages/contact/contact.html"*/'<ion-header>\n  <ion-navbar>\n    <ion-title>\n      Contact\n    </ion-title>\n  </ion-navbar>\n</ion-header>\n\n<ion-content>\n    <div>\n        <ion-item class="post-entry-message" text-wrap>\n            <img src="assets/imgs/isfic.jpg" />\n            <br/><br/>\n          <p>Boulkassoumbougou Près du Marché Rue 596 P 11 B.P: E2223 Bamako - Mali</p>\n          <p>Fixe/Fax : 20 24 02 66</p>\n          <p>Portable : 76 78 83 83 / 66 43 50 50 / 76 38 28 21</p>\n          <p>Site Web : www.isfic.ml</p>\n          <p>Email : isficb@gmail.com</p>\n          \n        </ion-item>\n      </div>\n      \n</ion-content>\n'/*ion-inline-end:"/home/dsarrus/project/studentcollect/src/pages/contact/contact.html"*/
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1_ionic_angular__["f" /* NavController */]])
    ], ContactPage);
    return ContactPage;
}());

//# sourceMappingURL=contact.js.map

/***/ }),

/***/ 205:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return HomePage; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_ionic_angular__ = __webpack_require__(29);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__providers_database_database__ = __webpack_require__(103);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__ionic_native_file__ = __webpack_require__(206);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__ionic_native_email_composer__ = __webpack_require__(207);
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};





var HomePage = /** @class */ (function () {
    function HomePage(navCtrl, toastCtrl, DB, file, emailComposer) {
        this.navCtrl = navCtrl;
        this.toastCtrl = toastCtrl;
        this.DB = DB;
        this.file = file;
        this.emailComposer = emailComposer;
        this.hasStudents = false;
    }
    HomePage.prototype.ionViewWillEnter = function () {
        this.displayStudents();
    };
    HomePage.prototype.displayStudents = function () {
        var _this = this;
        this.DB.retrieveStudents().then(function (data) {
            var existingData = Object.keys(data).length;
            if (existingData !== 0) {
                _this.hasStudents = true;
                _this.students = data;
                // alert(this.file.externalRootDirectory);
            }
            else {
                console.log("we get nada!");
            }
        });
    };
    HomePage.prototype.addCharacter = function () {
        this.navCtrl.push('AddPage');
    };
    HomePage.prototype.viewCharacter = function (param) {
        this.navCtrl.push('AddPage', param);
    };
    HomePage.prototype.testEmail = function () {
        var _this = this;
        var text = "ID, Nom, Prénom, Âge, Sexe, Quartier, Contact, Nom Parent, Contact Parent, Bac, Lycée, \n";
        for (var i = 0; i < this.students.length; i++) {
            text = text.concat(this.students[i].rev + ", " + this.students[i].nom + ", " + this.students[i].prenom + ", " + this.students[i].age + ", " + this.students[i].sexe + ", " + this.students[i].quartier + ", " + this.students[i].contact + ", " + this.students[i].nomParent + ", " + this.students[i].contactParent + ", " + this.students[i].bac + ", " + this.students[i].lycee + ",\n");
        }
        var filePath = this.file.externalRootDirectory;
        this.file.writeFile(filePath, 'studentData.csv', text, { replace: true })
            .then(function () {
            var email = {
                to: 'isficb@gmail.com',
                attachments: [
                    filePath + 'studentData.csv'
                ],
                subject: 'Envoi de données ' + new Date().toISOString(),
                body: '...',
                isHtml: true
            };
            _this.emailComposer.open(email);
            //this.sendNotification('Email envoyé avec succès !')
        })
            .catch(function (err) {
            console.error(err);
        });
    };
    HomePage.prototype.sendNotification = function (message) {
        var notification = this.toastCtrl.create({
            message: message,
            duration: 3000
        });
        notification.present();
    };
    HomePage = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["m" /* Component */])({
            selector: 'page-home',template:/*ion-inline-start:"/home/dsarrus/project/studentcollect/src/pages/home/home.html"*/'<ion-header>\n  <ion-navbar>\n    <ion-title>Accueil</ion-title>\n  </ion-navbar>\n</ion-header>\n\n<ion-content padding>\n\n\n  <ion-item>\n    <button class="add" ion-button item-right icon-right margin-bottom color="primary" (click)="addCharacter()">\n      NOUVEAU\n      <ion-icon name="add"></ion-icon>\n    </button>\n    <button class="add" ion-button item-right icon-right margin-bottom color="primary" (click)="testEmail()">\n        Envoi\n        <ion-icon name="send"></ion-icon>\n      </button>\n  </ion-item>\n\n\n\n  <div *ngIf="hasStudents">\n    <ion-list>\n\n      <ion-item *ngFor="let student of students">\n        \n\n        <!--ion-thumbnail item-left *ngIf = "student.image; then reaImg else noImg">\n            <img [src]="student.image">\n          </ion-thumbnail>\n        <ng-template #noImg>\n          <ion-thumbnail item-left #noImg>\n            <img width="100%" src="assets/imgs/logo.png" />\n          </ion-thumbnail>\n        </ng-template-->\n        <span>{{ student.prenom }} {{ student.nom }} </span>\n        <button ion-button clear item-right (click)="viewCharacter({\'key\':student.id, \'rev\':student.rev })">\n          Détails\n        </button>\n      </ion-item>\n\n    </ion-list>\n  </div>\n\n\n</ion-content>'/*ion-inline-end:"/home/dsarrus/project/studentcollect/src/pages/home/home.html"*/
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1_ionic_angular__["f" /* NavController */],
            __WEBPACK_IMPORTED_MODULE_1_ionic_angular__["i" /* ToastController */],
            __WEBPACK_IMPORTED_MODULE_2__providers_database_database__["a" /* DatabaseProvider */], __WEBPACK_IMPORTED_MODULE_3__ionic_native_file__["a" /* File */], __WEBPACK_IMPORTED_MODULE_4__ionic_native_email_composer__["a" /* EmailComposer */]])
    ], HomePage);
    return HomePage;
}());

//# sourceMappingURL=home.js.map

/***/ }),

/***/ 208:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ImageProvider; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_common_http__ = __webpack_require__(57);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_map__ = __webpack_require__(156);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_map___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_map__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__ionic_native_camera__ = __webpack_require__(157);
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




/*
  Generated class for the ImageProvider provider.

  See https://angular.io/guide/dependency-injection for more info on providers
  and Angular DI.
*/
var ImageProvider = /** @class */ (function () {
    function ImageProvider(http, _CAMERA) {
        this.http = http;
        this._CAMERA = _CAMERA;
        console.log('Hello ImageProvider Provider');
    }
    ImageProvider.prototype.takePhotograph = function () {
        var _this = this;
        return new Promise(function (resolve) {
            _this._CAMERA.getPicture({
                destinationType: _this._CAMERA.DestinationType.DATA_URL,
                targetWidth: 320,
                targetHeight: 240
            })
                .then(function (data) {
                _this.cameraImage = "data:image/jpeg;base64," + data;
                resolve(_this.cameraImage);
            });
        });
    };
    ImageProvider.prototype.selectPhotograph = function () {
        var _this = this;
        return new Promise(function (resolve) {
            var cameraOptions = {
                sourceType: _this._CAMERA.PictureSourceType.PHOTOLIBRARY,
                destinationType: _this._CAMERA.DestinationType.DATA_URL,
                quality: 100,
                targetWidth: 320,
                targetHeight: 240,
                encodingType: _this._CAMERA.EncodingType.JPEG,
                correctOrientation: true
            };
            _this._CAMERA.getPicture(cameraOptions)
                .then(function (data) {
                _this.cameraImage = "data:image/jpeg;base64," + data;
                resolve(_this.cameraImage);
            });
        });
    };
    ImageProvider = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_1__angular_core__["A" /* Injectable */])(),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_0__angular_common_http__["a" /* HttpClient */],
            __WEBPACK_IMPORTED_MODULE_3__ionic_native_camera__["a" /* Camera */]])
    ], ImageProvider);
    return ImageProvider;
}());

//# sourceMappingURL=image.js.map

/***/ }),

/***/ 209:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_platform_browser_dynamic__ = __webpack_require__(210);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__app_module__ = __webpack_require__(230);


Object(__WEBPACK_IMPORTED_MODULE_0__angular_platform_browser_dynamic__["a" /* platformBrowserDynamic */])().bootstrapModule(__WEBPACK_IMPORTED_MODULE_1__app_module__["a" /* AppModule */]);
//# sourceMappingURL=main.js.map

/***/ }),

/***/ 230:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AppModule; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_http__ = __webpack_require__(231);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_common_http__ = __webpack_require__(57);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__angular_platform_browser__ = __webpack_require__(26);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_ionic_angular__ = __webpack_require__(29);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__app_component__ = __webpack_require__(296);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__ionic_native_camera__ = __webpack_require__(157);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__ionic_native_file__ = __webpack_require__(206);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__ionic_native_email_composer__ = __webpack_require__(207);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9__pages_about_about__ = __webpack_require__(203);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10__pages_contact_contact__ = __webpack_require__(204);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_11__pages_home_home__ = __webpack_require__(205);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_12__pages_tabs_tabs__ = __webpack_require__(202);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_13__ionic_native_status_bar__ = __webpack_require__(200);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_14__ionic_native_splash_screen__ = __webpack_require__(201);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_15__providers_database_database__ = __webpack_require__(103);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_16__providers_image_image__ = __webpack_require__(208);
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};

















var AppModule = /** @class */ (function () {
    function AppModule() {
    }
    AppModule = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["I" /* NgModule */])({
            declarations: [
                __WEBPACK_IMPORTED_MODULE_5__app_component__["a" /* MyApp */],
                __WEBPACK_IMPORTED_MODULE_9__pages_about_about__["a" /* AboutPage */],
                __WEBPACK_IMPORTED_MODULE_10__pages_contact_contact__["a" /* ContactPage */],
                __WEBPACK_IMPORTED_MODULE_11__pages_home_home__["a" /* HomePage */],
                __WEBPACK_IMPORTED_MODULE_12__pages_tabs_tabs__["a" /* TabsPage */]
            ],
            imports: [
                __WEBPACK_IMPORTED_MODULE_3__angular_platform_browser__["a" /* BrowserModule */],
                __WEBPACK_IMPORTED_MODULE_1__angular_http__["a" /* HttpModule */],
                __WEBPACK_IMPORTED_MODULE_2__angular_common_http__["b" /* HttpClientModule */],
                __WEBPACK_IMPORTED_MODULE_4_ionic_angular__["d" /* IonicModule */].forRoot(__WEBPACK_IMPORTED_MODULE_5__app_component__["a" /* MyApp */], {}, {
                    links: [
                        { loadChildren: '../pages/add/add.module#AddPageModule', name: 'AddPage', segment: 'add', priority: 'low', defaultHistory: [] }
                    ]
                })
            ],
            bootstrap: [__WEBPACK_IMPORTED_MODULE_4_ionic_angular__["b" /* IonicApp */]],
            entryComponents: [
                __WEBPACK_IMPORTED_MODULE_5__app_component__["a" /* MyApp */],
                __WEBPACK_IMPORTED_MODULE_9__pages_about_about__["a" /* AboutPage */],
                __WEBPACK_IMPORTED_MODULE_10__pages_contact_contact__["a" /* ContactPage */],
                __WEBPACK_IMPORTED_MODULE_11__pages_home_home__["a" /* HomePage */],
                __WEBPACK_IMPORTED_MODULE_12__pages_tabs_tabs__["a" /* TabsPage */]
            ],
            providers: [
                __WEBPACK_IMPORTED_MODULE_13__ionic_native_status_bar__["a" /* StatusBar */],
                __WEBPACK_IMPORTED_MODULE_14__ionic_native_splash_screen__["a" /* SplashScreen */],
                { provide: __WEBPACK_IMPORTED_MODULE_0__angular_core__["u" /* ErrorHandler */], useClass: __WEBPACK_IMPORTED_MODULE_4_ionic_angular__["c" /* IonicErrorHandler */] },
                __WEBPACK_IMPORTED_MODULE_6__ionic_native_camera__["a" /* Camera */],
                __WEBPACK_IMPORTED_MODULE_7__ionic_native_file__["a" /* File */],
                __WEBPACK_IMPORTED_MODULE_8__ionic_native_email_composer__["a" /* EmailComposer */],
                __WEBPACK_IMPORTED_MODULE_15__providers_database_database__["a" /* DatabaseProvider */],
                __WEBPACK_IMPORTED_MODULE_16__providers_image_image__["a" /* ImageProvider */]
            ]
        })
    ], AppModule);
    return AppModule;
}());

//# sourceMappingURL=app.module.js.map

/***/ }),

/***/ 296:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return MyApp; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_ionic_angular__ = __webpack_require__(29);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__ionic_native_status_bar__ = __webpack_require__(200);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__ionic_native_splash_screen__ = __webpack_require__(201);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__pages_tabs_tabs__ = __webpack_require__(202);
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};





var MyApp = /** @class */ (function () {
    function MyApp(platform, statusBar, splashScreen) {
        this.rootPage = __WEBPACK_IMPORTED_MODULE_4__pages_tabs_tabs__["a" /* TabsPage */];
        platform.ready().then(function () {
            // Okay, so the platform is ready and our plugins are available.
            // Here you can do any higher level native things you might need.
            statusBar.styleDefault();
            splashScreen.hide();
        });
    }
    MyApp = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["m" /* Component */])({template:/*ion-inline-start:"/home/dsarrus/project/studentcollect/src/app/app.html"*/'<ion-nav [root]="rootPage"></ion-nav>\n'/*ion-inline-end:"/home/dsarrus/project/studentcollect/src/app/app.html"*/
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1_ionic_angular__["h" /* Platform */], __WEBPACK_IMPORTED_MODULE_2__ionic_native_status_bar__["a" /* StatusBar */], __WEBPACK_IMPORTED_MODULE_3__ionic_native_splash_screen__["a" /* SplashScreen */]])
    ], MyApp);
    return MyApp;
}());

//# sourceMappingURL=app.component.js.map

/***/ })

},[209]);
//# sourceMappingURL=main.js.map