(window["webpackJsonp"] = window["webpackJsonp"] || []).push([[67],{

/***/ "./src/main/webapp/app/admin/tracker/tracker.component.ts":
/*!****************************************************************!*\
  !*** ./src/main/webapp/app/admin/tracker/tracker.component.ts ***!
  \****************************************************************/
/*! exports provided: TrackerComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
eval("__webpack_require__.r(__webpack_exports__);\n/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, \"TrackerComponent\", function() { return TrackerComponent; });\n/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ \"./node_modules/@angular/core/__ivy_ngcc__/fesm2015/core.js\");\n/* harmony import */ var app_core_tracker_tracker_service__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! app/core/tracker/tracker.service */ \"./src/main/webapp/app/core/tracker/tracker.service.ts\");\n/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/common */ \"./node_modules/@angular/common/__ivy_ngcc__/fesm2015/common.js\");\n\n\n\n\n\nfunction TrackerComponent_tr_31_Template(rf, ctx) { if (rf & 1) {\n    _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵelementStart\"](0, \"tr\");\n    _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵtext\"](1, \"\\n                    \");\n    _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵelementStart\"](2, \"td\");\n    _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵtext\"](3);\n    _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵelementEnd\"]();\n    _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵtext\"](4, \"\\n                    \");\n    _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵelementStart\"](5, \"td\");\n    _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵtext\"](6);\n    _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵelementEnd\"]();\n    _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵtext\"](7, \"\\n                    \");\n    _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵelementStart\"](8, \"td\");\n    _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵtext\"](9);\n    _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵelementEnd\"]();\n    _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵtext\"](10, \"\\n                    \");\n    _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵelementStart\"](11, \"td\");\n    _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵtext\"](12);\n    _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵpipe\"](13, \"date\");\n    _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵelementEnd\"]();\n    _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵtext\"](14, \"\\n                \");\n    _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵelementEnd\"]();\n} if (rf & 2) {\n    const activity_r1 = ctx.$implicit;\n    _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵadvance\"](3);\n    _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵtextInterpolate\"](activity_r1.userLogin);\n    _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵadvance\"](3);\n    _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵtextInterpolate\"](activity_r1.ipAddress);\n    _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵadvance\"](3);\n    _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵtextInterpolate\"](activity_r1.page);\n    _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵadvance\"](3);\n    _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵtextInterpolate\"](_angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵpipeBind2\"](13, 4, activity_r1.time, \"yyyy-MM-dd HH:mm:ss\"));\n} }\nclass TrackerComponent {\n    constructor(trackerService) {\n        this.trackerService = trackerService;\n        this.activities = [];\n    }\n    showActivity(activity) {\n        let existingActivity = false;\n        for (let index = 0; index < this.activities.length; index++) {\n            if (this.activities[index].sessionId === activity.sessionId) {\n                existingActivity = true;\n                if (activity.page === 'logout') {\n                    this.activities.splice(index, 1);\n                }\n                else {\n                    this.activities[index] = activity;\n                }\n            }\n        }\n        if (!existingActivity && activity.page !== 'logout') {\n            this.activities.push(activity);\n        }\n    }\n    ngOnInit() {\n        this.trackerService.subscribe();\n        this.subscription = this.trackerService.receive().subscribe((activity) => {\n            this.showActivity(activity);\n        });\n    }\n    ngOnDestroy() {\n        this.trackerService.unsubscribe();\n        if (this.subscription) {\n            this.subscription.unsubscribe();\n        }\n    }\n}\nTrackerComponent.ɵfac = function TrackerComponent_Factory(t) { return new (t || TrackerComponent)(_angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵdirectiveInject\"](app_core_tracker_tracker_service__WEBPACK_IMPORTED_MODULE_1__[\"TrackerService\"])); };\nTrackerComponent.ɵcmp = _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵdefineComponent\"]({ type: TrackerComponent, selectors: [[\"jhi-tracker\"]], decls: 37, vars: 1, consts: [[\"id\", \"tracker-page-heading\"], [1, \"table-responsive\"], [\"aria-describedby\", \"tracker-page-heading\", 1, \"table\", \"table-striped\"], [\"scope\", \"col\"], [4, \"ngFor\", \"ngForOf\"]], template: function TrackerComponent_Template(rf, ctx) { if (rf & 1) {\n        _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵelementStart\"](0, \"div\");\n        _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵtext\"](1, \"\\n    \");\n        _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵelementStart\"](2, \"h2\", 0);\n        _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵtext\"](3, \"Real-time user activities\");\n        _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵelementEnd\"]();\n        _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵtext\"](4, \"\\n\\n    \");\n        _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵelementStart\"](5, \"div\", 1);\n        _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵtext\"](6, \"\\n        \");\n        _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵelementStart\"](7, \"table\", 2);\n        _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵtext\"](8, \"\\n            \");\n        _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵelementStart\"](9, \"thead\");\n        _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵtext\"](10, \"\\n                \");\n        _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵelementStart\"](11, \"tr\");\n        _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵtext\"](12, \"\\n                    \");\n        _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵelementStart\"](13, \"th\", 3);\n        _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵtext\"](14, \"User\");\n        _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵelementEnd\"]();\n        _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵtext\"](15, \"\\n                    \");\n        _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵelementStart\"](16, \"th\", 3);\n        _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵtext\"](17, \"IP Address\");\n        _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵelementEnd\"]();\n        _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵtext\"](18, \"\\n                    \");\n        _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵelementStart\"](19, \"th\", 3);\n        _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵtext\"](20, \"Current page\");\n        _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵelementEnd\"]();\n        _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵtext\"](21, \"\\n                    \");\n        _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵelementStart\"](22, \"th\", 3);\n        _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵtext\"](23, \"Time\");\n        _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵelementEnd\"]();\n        _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵtext\"](24, \"\\n                    \");\n        _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵelement\"](25, \"th\", 3);\n        _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵtext\"](26, \"\\n                \");\n        _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵelementEnd\"]();\n        _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵtext\"](27, \"\\n            \");\n        _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵelementEnd\"]();\n        _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵtext\"](28, \"\\n            \");\n        _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵelementStart\"](29, \"tbody\");\n        _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵtext\"](30, \"\\n                \");\n        _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵtemplate\"](31, TrackerComponent_tr_31_Template, 15, 7, \"tr\", 4);\n        _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵtext\"](32, \"\\n            \");\n        _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵelementEnd\"]();\n        _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵtext\"](33, \"\\n        \");\n        _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵelementEnd\"]();\n        _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵtext\"](34, \"\\n    \");\n        _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵelementEnd\"]();\n        _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵtext\"](35, \"\\n\");\n        _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵelementEnd\"]();\n        _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵtext\"](36, \"\\n\");\n    } if (rf & 2) {\n        _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵadvance\"](31);\n        _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵproperty\"](\"ngForOf\", ctx.activities);\n    } }, directives: [_angular_common__WEBPACK_IMPORTED_MODULE_2__[\"NgForOf\"]], pipes: [_angular_common__WEBPACK_IMPORTED_MODULE_2__[\"DatePipe\"]], encapsulation: 2 });\n/*@__PURE__*/ (function () { _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵsetClassMetadata\"](TrackerComponent, [{\n        type: _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"Component\"],\n        args: [{\n                selector: 'jhi-tracker',\n                templateUrl: './tracker.component.html',\n            }]\n    }], function () { return [{ type: app_core_tracker_tracker_service__WEBPACK_IMPORTED_MODULE_1__[\"TrackerService\"] }]; }, null); })();\n//# sourceURL=[module]\n//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbIndlYnBhY2s6Ly8vLi9zcmMvbWFpbi93ZWJhcHAvYXBwL2FkbWluL3RyYWNrZXIvdHJhY2tlci5jb21wb25lbnQudHM/Mzc3YSIsIndlYnBhY2s6Ly8vLi9zcmMvbWFpbi93ZWJhcHAvYXBwL2FkbWluL3RyYWNrZXIvdHJhY2tlci5jb21wb25lbnQuaHRtbD8xNWJhIl0sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiJBQUFBO0FBQUE7QUFBQTtBQUFBO0FBQUE7QUFBNkQ7QUFHSzs7Ozs7SUNZbEQscUVBQ0k7SUFBQTtJQUFBLHFFQUFJO0lBQUEsdURBQXdCO0lBQUEsNERBQUs7SUFDakM7SUFBQSxxRUFBSTtJQUFBLHVEQUF3QjtJQUFBLDREQUFLO0lBQ2pDO0lBQUEscUVBQUk7SUFBQSx1REFBbUI7SUFBQSw0REFBSztJQUM1QjtJQUFBLHNFQUFJO0lBQUEsd0RBQWdEOztJQUFBLDREQUFLO0lBQzdEO0lBQUEsNERBQUs7OztJQUpHLDBEQUF3QjtJQUF4QixzRkFBd0I7SUFDeEIsMERBQXdCO0lBQXhCLHNGQUF3QjtJQUN4QiwwREFBbUI7SUFBbkIsaUZBQW1CO0lBQ25CLDBEQUFnRDtJQUFoRCwwS0FBZ0Q7O0FEVGpFLE1BQU0sZ0JBQWdCO0lBSTNCLFlBQW9CLGNBQThCO1FBQTlCLG1CQUFjLEdBQWQsY0FBYyxDQUFnQjtRQUhsRCxlQUFVLEdBQXNCLEVBQUUsQ0FBQztJQUdrQixDQUFDO0lBRXRELFlBQVksQ0FBQyxRQUF5QjtRQUNwQyxJQUFJLGdCQUFnQixHQUFHLEtBQUssQ0FBQztRQUU3QixLQUFLLElBQUksS0FBSyxHQUFHLENBQUMsRUFBRSxLQUFLLEdBQUcsSUFBSSxDQUFDLFVBQVUsQ0FBQyxNQUFNLEVBQUUsS0FBSyxFQUFFLEVBQUU7WUFDM0QsSUFBSSxJQUFJLENBQUMsVUFBVSxDQUFDLEtBQUssQ0FBQyxDQUFDLFNBQVMsS0FBSyxRQUFRLENBQUMsU0FBUyxFQUFFO2dCQUMzRCxnQkFBZ0IsR0FBRyxJQUFJLENBQUM7Z0JBQ3hCLElBQUksUUFBUSxDQUFDLElBQUksS0FBSyxRQUFRLEVBQUU7b0JBQzlCLElBQUksQ0FBQyxVQUFVLENBQUMsTUFBTSxDQUFDLEtBQUssRUFBRSxDQUFDLENBQUMsQ0FBQztpQkFDbEM7cUJBQU07b0JBQ0wsSUFBSSxDQUFDLFVBQVUsQ0FBQyxLQUFLLENBQUMsR0FBRyxRQUFRLENBQUM7aUJBQ25DO2FBQ0Y7U0FDRjtRQUVELElBQUksQ0FBQyxnQkFBZ0IsSUFBSSxRQUFRLENBQUMsSUFBSSxLQUFLLFFBQVEsRUFBRTtZQUNuRCxJQUFJLENBQUMsVUFBVSxDQUFDLElBQUksQ0FBQyxRQUFRLENBQUMsQ0FBQztTQUNoQztJQUNILENBQUM7SUFFRCxRQUFRO1FBQ04sSUFBSSxDQUFDLGNBQWMsQ0FBQyxTQUFTLEVBQUUsQ0FBQztRQUNoQyxJQUFJLENBQUMsWUFBWSxHQUFHLElBQUksQ0FBQyxjQUFjLENBQUMsT0FBTyxFQUFFLENBQUMsU0FBUyxDQUFDLENBQUMsUUFBeUIsRUFBRSxFQUFFO1lBQ3hGLElBQUksQ0FBQyxZQUFZLENBQUMsUUFBUSxDQUFDLENBQUM7UUFDOUIsQ0FBQyxDQUFDLENBQUM7SUFDTCxDQUFDO0lBRUQsV0FBVztRQUNULElBQUksQ0FBQyxjQUFjLENBQUMsV0FBVyxFQUFFLENBQUM7UUFDbEMsSUFBSSxJQUFJLENBQUMsWUFBWSxFQUFFO1lBQ3JCLElBQUksQ0FBQyxZQUFZLENBQUMsV0FBVyxFQUFFLENBQUM7U0FDakM7SUFDSCxDQUFDOztnRkFyQ1UsZ0JBQWdCO2dHQUFoQixnQkFBZ0I7UUNWN0Isc0VBQ0k7UUFBQTtRQUFBLHdFQUE4QjtRQUFBLG9GQUF5QjtRQUFBLDREQUFLO1FBRTVEO1FBQUEseUVBQ0k7UUFBQTtRQUFBLDJFQUNJO1FBQUE7UUFBQSx3RUFDSTtRQUFBO1FBQUEsc0VBQ0k7UUFBQTtRQUFBLHlFQUFnQjtRQUFBLGdFQUFJO1FBQUEsNERBQUs7UUFDekI7UUFBQSx5RUFBZ0I7UUFBQSxzRUFBVTtRQUFBLDREQUFLO1FBQy9CO1FBQUEseUVBQWdCO1FBQUEsd0VBQVk7UUFBQSw0REFBSztRQUNqQztRQUFBLHlFQUFnQjtRQUFBLGdFQUFJO1FBQUEsNERBQUs7UUFDekI7UUFBQSxvRUFBcUI7UUFDekI7UUFBQSw0REFBSztRQUNUO1FBQUEsNERBQVE7UUFDUjtRQUFBLHlFQUNJO1FBQUE7UUFBQSw2R0FDSTtRQUtSO1FBQUEsNERBQVE7UUFDWjtRQUFBLDREQUFRO1FBQ1o7UUFBQSw0REFBTTtRQUNWO1FBQUEsNERBQU07UUFDTjs7UUFWb0IsMkRBQW1DO1FBQW5DLG1GQUFtQzs7NkZETDFDLGdCQUFnQjtjQUo1Qix1REFBUztlQUFDO2dCQUNULFFBQVEsRUFBRSxhQUFhO2dCQUN2QixXQUFXLEVBQUUsMEJBQTBCO2FBQ3hDIiwiZmlsZSI6Ii4vc3JjL21haW4vd2ViYXBwL2FwcC9hZG1pbi90cmFja2VyL3RyYWNrZXIuY29tcG9uZW50LnRzLmpzIiwic291cmNlc0NvbnRlbnQiOlsiaW1wb3J0IHsgQ29tcG9uZW50LCBPbkluaXQsIE9uRGVzdHJveSB9IGZyb20gJ0Bhbmd1bGFyL2NvcmUnO1xyXG5pbXBvcnQgeyBTdWJzY3JpcHRpb24gfSBmcm9tICdyeGpzJztcclxuXHJcbmltcG9ydCB7IFRyYWNrZXJTZXJ2aWNlIH0gZnJvbSAnYXBwL2NvcmUvdHJhY2tlci90cmFja2VyLnNlcnZpY2UnO1xyXG5pbXBvcnQgeyBUcmFja2VyQWN0aXZpdHkgfSBmcm9tICdhcHAvY29yZS90cmFja2VyL3RyYWNrZXItYWN0aXZpdHkubW9kZWwnO1xyXG5cclxuQENvbXBvbmVudCh7XHJcbiAgc2VsZWN0b3I6ICdqaGktdHJhY2tlcicsXHJcbiAgdGVtcGxhdGVVcmw6ICcuL3RyYWNrZXIuY29tcG9uZW50Lmh0bWwnLFxyXG59KVxyXG5leHBvcnQgY2xhc3MgVHJhY2tlckNvbXBvbmVudCBpbXBsZW1lbnRzIE9uSW5pdCwgT25EZXN0cm95IHtcclxuICBhY3Rpdml0aWVzOiBUcmFja2VyQWN0aXZpdHlbXSA9IFtdO1xyXG4gIHN1YnNjcmlwdGlvbj86IFN1YnNjcmlwdGlvbjtcclxuXHJcbiAgY29uc3RydWN0b3IocHJpdmF0ZSB0cmFja2VyU2VydmljZTogVHJhY2tlclNlcnZpY2UpIHt9XHJcblxyXG4gIHNob3dBY3Rpdml0eShhY3Rpdml0eTogVHJhY2tlckFjdGl2aXR5KTogdm9pZCB7XHJcbiAgICBsZXQgZXhpc3RpbmdBY3Rpdml0eSA9IGZhbHNlO1xyXG5cclxuICAgIGZvciAobGV0IGluZGV4ID0gMDsgaW5kZXggPCB0aGlzLmFjdGl2aXRpZXMubGVuZ3RoOyBpbmRleCsrKSB7XHJcbiAgICAgIGlmICh0aGlzLmFjdGl2aXRpZXNbaW5kZXhdLnNlc3Npb25JZCA9PT0gYWN0aXZpdHkuc2Vzc2lvbklkKSB7XHJcbiAgICAgICAgZXhpc3RpbmdBY3Rpdml0eSA9IHRydWU7XHJcbiAgICAgICAgaWYgKGFjdGl2aXR5LnBhZ2UgPT09ICdsb2dvdXQnKSB7XHJcbiAgICAgICAgICB0aGlzLmFjdGl2aXRpZXMuc3BsaWNlKGluZGV4LCAxKTtcclxuICAgICAgICB9IGVsc2Uge1xyXG4gICAgICAgICAgdGhpcy5hY3Rpdml0aWVzW2luZGV4XSA9IGFjdGl2aXR5O1xyXG4gICAgICAgIH1cclxuICAgICAgfVxyXG4gICAgfVxyXG5cclxuICAgIGlmICghZXhpc3RpbmdBY3Rpdml0eSAmJiBhY3Rpdml0eS5wYWdlICE9PSAnbG9nb3V0Jykge1xyXG4gICAgICB0aGlzLmFjdGl2aXRpZXMucHVzaChhY3Rpdml0eSk7XHJcbiAgICB9XHJcbiAgfVxyXG5cclxuICBuZ09uSW5pdCgpOiB2b2lkIHtcclxuICAgIHRoaXMudHJhY2tlclNlcnZpY2Uuc3Vic2NyaWJlKCk7XHJcbiAgICB0aGlzLnN1YnNjcmlwdGlvbiA9IHRoaXMudHJhY2tlclNlcnZpY2UucmVjZWl2ZSgpLnN1YnNjcmliZSgoYWN0aXZpdHk6IFRyYWNrZXJBY3Rpdml0eSkgPT4ge1xyXG4gICAgICB0aGlzLnNob3dBY3Rpdml0eShhY3Rpdml0eSk7XHJcbiAgICB9KTtcclxuICB9XHJcblxyXG4gIG5nT25EZXN0cm95KCk6IHZvaWQge1xyXG4gICAgdGhpcy50cmFja2VyU2VydmljZS51bnN1YnNjcmliZSgpO1xyXG4gICAgaWYgKHRoaXMuc3Vic2NyaXB0aW9uKSB7XHJcbiAgICAgIHRoaXMuc3Vic2NyaXB0aW9uLnVuc3Vic2NyaWJlKCk7XHJcbiAgICB9XHJcbiAgfVxyXG59XHJcbiIsIjxkaXY+XHJcbiAgICA8aDIgaWQ9XCJ0cmFja2VyLXBhZ2UtaGVhZGluZ1wiPlJlYWwtdGltZSB1c2VyIGFjdGl2aXRpZXM8L2gyPlxyXG5cclxuICAgIDxkaXYgY2xhc3M9XCJ0YWJsZS1yZXNwb25zaXZlXCI+XHJcbiAgICAgICAgPHRhYmxlIGNsYXNzPVwidGFibGUgdGFibGUtc3RyaXBlZFwiIGFyaWEtZGVzY3JpYmVkYnk9XCJ0cmFja2VyLXBhZ2UtaGVhZGluZ1wiPlxyXG4gICAgICAgICAgICA8dGhlYWQ+XHJcbiAgICAgICAgICAgICAgICA8dHI+XHJcbiAgICAgICAgICAgICAgICAgICAgPHRoIHNjb3BlPVwiY29sXCI+VXNlcjwvdGg+XHJcbiAgICAgICAgICAgICAgICAgICAgPHRoIHNjb3BlPVwiY29sXCI+SVAgQWRkcmVzczwvdGg+XHJcbiAgICAgICAgICAgICAgICAgICAgPHRoIHNjb3BlPVwiY29sXCI+Q3VycmVudCBwYWdlPC90aD5cclxuICAgICAgICAgICAgICAgICAgICA8dGggc2NvcGU9XCJjb2xcIj5UaW1lPC90aD5cclxuICAgICAgICAgICAgICAgICAgICA8dGggc2NvcGU9XCJjb2xcIj48L3RoPlxyXG4gICAgICAgICAgICAgICAgPC90cj5cclxuICAgICAgICAgICAgPC90aGVhZD5cclxuICAgICAgICAgICAgPHRib2R5PlxyXG4gICAgICAgICAgICAgICAgPHRyICpuZ0Zvcj1cImxldCBhY3Rpdml0eSBvZiBhY3Rpdml0aWVzXCI+XHJcbiAgICAgICAgICAgICAgICAgICAgPHRkPnt7IGFjdGl2aXR5LnVzZXJMb2dpbiB9fTwvdGQ+XHJcbiAgICAgICAgICAgICAgICAgICAgPHRkPnt7IGFjdGl2aXR5LmlwQWRkcmVzcyB9fTwvdGQ+XHJcbiAgICAgICAgICAgICAgICAgICAgPHRkPnt7IGFjdGl2aXR5LnBhZ2UgfX08L3RkPlxyXG4gICAgICAgICAgICAgICAgICAgIDx0ZD57eyBhY3Rpdml0eS50aW1lIHwgZGF0ZToneXl5eS1NTS1kZCBISDptbTpzcycgfX08L3RkPlxyXG4gICAgICAgICAgICAgICAgPC90cj5cclxuICAgICAgICAgICAgPC90Ym9keT5cclxuICAgICAgICA8L3RhYmxlPlxyXG4gICAgPC9kaXY+XHJcbjwvZGl2PlxyXG4iXSwic291cmNlUm9vdCI6IiJ9\n//# sourceURL=webpack-internal:///./src/main/webapp/app/admin/tracker/tracker.component.ts\n");

/***/ }),

/***/ "./src/main/webapp/app/admin/tracker/tracker.module.ts":
/*!*************************************************************!*\
  !*** ./src/main/webapp/app/admin/tracker/tracker.module.ts ***!
  \*************************************************************/
/*! exports provided: TrackerModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
eval("__webpack_require__.r(__webpack_exports__);\n/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, \"TrackerModule\", function() { return TrackerModule; });\n/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ \"./node_modules/@angular/core/__ivy_ngcc__/fesm2015/core.js\");\n/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/router */ \"./node_modules/@angular/router/__ivy_ngcc__/fesm2015/router.js\");\n/* harmony import */ var app_shared_shared_module__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! app/shared/shared.module */ \"./src/main/webapp/app/shared/shared.module.ts\");\n/* harmony import */ var _tracker_component__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./tracker.component */ \"./src/main/webapp/app/admin/tracker/tracker.component.ts\");\n/* harmony import */ var _tracker_route__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./tracker.route */ \"./src/main/webapp/app/admin/tracker/tracker.route.ts\");\n\n\n\n\n\n\n\nclass TrackerModule {\n}\nTrackerModule.ɵmod = _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵdefineNgModule\"]({ type: TrackerModule });\nTrackerModule.ɵinj = _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵdefineInjector\"]({ factory: function TrackerModule_Factory(t) { return new (t || TrackerModule)(); }, imports: [[app_shared_shared_module__WEBPACK_IMPORTED_MODULE_2__[\"CodeNodeErpSharedModule\"], _angular_router__WEBPACK_IMPORTED_MODULE_1__[\"RouterModule\"].forChild([_tracker_route__WEBPACK_IMPORTED_MODULE_4__[\"trackerRoute\"]])]] });\n(function () { (typeof ngJitMode === \"undefined\" || ngJitMode) && _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵɵsetNgModuleScope\"](TrackerModule, { declarations: [_tracker_component__WEBPACK_IMPORTED_MODULE_3__[\"TrackerComponent\"]], imports: [app_shared_shared_module__WEBPACK_IMPORTED_MODULE_2__[\"CodeNodeErpSharedModule\"], _angular_router__WEBPACK_IMPORTED_MODULE_1__[\"RouterModule\"]] }); })();\n/*@__PURE__*/ (function () { _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"ɵsetClassMetadata\"](TrackerModule, [{\n        type: _angular_core__WEBPACK_IMPORTED_MODULE_0__[\"NgModule\"],\n        args: [{\n                imports: [app_shared_shared_module__WEBPACK_IMPORTED_MODULE_2__[\"CodeNodeErpSharedModule\"], _angular_router__WEBPACK_IMPORTED_MODULE_1__[\"RouterModule\"].forChild([_tracker_route__WEBPACK_IMPORTED_MODULE_4__[\"trackerRoute\"]])],\n                declarations: [_tracker_component__WEBPACK_IMPORTED_MODULE_3__[\"TrackerComponent\"]],\n            }]\n    }], null, null); })();\n//# sourceURL=[module]\n//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbIndlYnBhY2s6Ly8vLi9zcmMvbWFpbi93ZWJhcHAvYXBwL2FkbWluL3RyYWNrZXIvdHJhY2tlci5tb2R1bGUudHM/MzRmMyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiQUFBQTtBQUFBO0FBQUE7QUFBQTtBQUFBO0FBQUE7QUFBQTtBQUF5QztBQUNNO0FBQ29CO0FBRVo7QUFFUjs7O0FBTXhDLE1BQU0sYUFBYTs7NEZBQWIsYUFBYTtvSkFBYixhQUFhLGtCQUhmLENBQUMsZ0ZBQXVCLEVBQUUsNERBQVksQ0FBQyxRQUFRLENBQUMsQ0FBQywyREFBWSxDQUFDLENBQUMsQ0FBQzttSUFHOUQsYUFBYSxtQkFGVCxtRUFBZ0IsYUFEckIsZ0ZBQXVCLEVBQUU7NkZBR3hCLGFBQWE7Y0FKekIsc0RBQVE7ZUFBQztnQkFDUixPQUFPLEVBQUUsQ0FBQyxnRkFBdUIsRUFBRSw0REFBWSxDQUFDLFFBQVEsQ0FBQyxDQUFDLDJEQUFZLENBQUMsQ0FBQyxDQUFDO2dCQUN6RSxZQUFZLEVBQUUsQ0FBQyxtRUFBZ0IsQ0FBQzthQUNqQyIsImZpbGUiOiIuL3NyYy9tYWluL3dlYmFwcC9hcHAvYWRtaW4vdHJhY2tlci90cmFja2VyLm1vZHVsZS50cy5qcyIsInNvdXJjZXNDb250ZW50IjpbImltcG9ydCB7IE5nTW9kdWxlIH0gZnJvbSAnQGFuZ3VsYXIvY29yZSc7XHJcbmltcG9ydCB7IFJvdXRlck1vZHVsZSB9IGZyb20gJ0Bhbmd1bGFyL3JvdXRlcic7XHJcbmltcG9ydCB7IENvZGVOb2RlRXJwU2hhcmVkTW9kdWxlIH0gZnJvbSAnYXBwL3NoYXJlZC9zaGFyZWQubW9kdWxlJztcclxuXHJcbmltcG9ydCB7IFRyYWNrZXJDb21wb25lbnQgfSBmcm9tICcuL3RyYWNrZXIuY29tcG9uZW50JztcclxuXHJcbmltcG9ydCB7IHRyYWNrZXJSb3V0ZSB9IGZyb20gJy4vdHJhY2tlci5yb3V0ZSc7XHJcblxyXG5ATmdNb2R1bGUoe1xyXG4gIGltcG9ydHM6IFtDb2RlTm9kZUVycFNoYXJlZE1vZHVsZSwgUm91dGVyTW9kdWxlLmZvckNoaWxkKFt0cmFja2VyUm91dGVdKV0sXHJcbiAgZGVjbGFyYXRpb25zOiBbVHJhY2tlckNvbXBvbmVudF0sXHJcbn0pXHJcbmV4cG9ydCBjbGFzcyBUcmFja2VyTW9kdWxlIHt9XHJcbiJdLCJzb3VyY2VSb290IjoiIn0=\n//# sourceURL=webpack-internal:///./src/main/webapp/app/admin/tracker/tracker.module.ts\n");

/***/ }),

/***/ "./src/main/webapp/app/admin/tracker/tracker.route.ts":
/*!************************************************************!*\
  !*** ./src/main/webapp/app/admin/tracker/tracker.route.ts ***!
  \************************************************************/
/*! exports provided: trackerRoute */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
eval("__webpack_require__.r(__webpack_exports__);\n/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, \"trackerRoute\", function() { return trackerRoute; });\n/* harmony import */ var _tracker_component__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./tracker.component */ \"./src/main/webapp/app/admin/tracker/tracker.component.ts\");\n\nconst trackerRoute = {\n    path: '',\n    component: _tracker_component__WEBPACK_IMPORTED_MODULE_0__[\"TrackerComponent\"],\n    data: {\n        pageTitle: 'Real-time user activities',\n    },\n};\n//# sourceURL=[module]\n//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbIndlYnBhY2s6Ly8vLi9zcmMvbWFpbi93ZWJhcHAvYXBwL2FkbWluL3RyYWNrZXIvdHJhY2tlci5yb3V0ZS50cz83YjYzIl0sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiJBQUVBO0FBQUE7QUFBQTtBQUF1RDtBQUVoRCxNQUFNLFlBQVksR0FBVTtJQUNqQyxJQUFJLEVBQUUsRUFBRTtJQUNSLFNBQVMsRUFBRSxtRUFBZ0I7SUFDM0IsSUFBSSxFQUFFO1FBQ0osU0FBUyxFQUFFLDJCQUEyQjtLQUN2QztDQUNGLENBQUMiLCJmaWxlIjoiLi9zcmMvbWFpbi93ZWJhcHAvYXBwL2FkbWluL3RyYWNrZXIvdHJhY2tlci5yb3V0ZS50cy5qcyIsInNvdXJjZXNDb250ZW50IjpbImltcG9ydCB7IFJvdXRlIH0gZnJvbSAnQGFuZ3VsYXIvcm91dGVyJztcclxuXHJcbmltcG9ydCB7IFRyYWNrZXJDb21wb25lbnQgfSBmcm9tICcuL3RyYWNrZXIuY29tcG9uZW50JztcclxuXHJcbmV4cG9ydCBjb25zdCB0cmFja2VyUm91dGU6IFJvdXRlID0ge1xyXG4gIHBhdGg6ICcnLFxyXG4gIGNvbXBvbmVudDogVHJhY2tlckNvbXBvbmVudCxcclxuICBkYXRhOiB7XHJcbiAgICBwYWdlVGl0bGU6ICdSZWFsLXRpbWUgdXNlciBhY3Rpdml0aWVzJyxcclxuICB9LFxyXG59O1xyXG4iXSwic291cmNlUm9vdCI6IiJ9\n//# sourceURL=webpack-internal:///./src/main/webapp/app/admin/tracker/tracker.route.ts\n");

/***/ })

}]);