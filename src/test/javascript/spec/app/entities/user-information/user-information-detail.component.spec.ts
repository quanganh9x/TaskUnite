import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TaskUniteTestModule } from '../../../test.module';
import { UserInformationDetailComponent } from 'app/entities/user-information/user-information-detail.component';
import { UserInformation } from 'app/shared/model/user-information.model';

describe('Component Tests', () => {
  describe('UserInformation Management Detail Component', () => {
    let comp: UserInformationDetailComponent;
    let fixture: ComponentFixture<UserInformationDetailComponent>;
    const route = ({ data: of({ userInformation: new UserInformation(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TaskUniteTestModule],
        declarations: [UserInformationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(UserInformationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserInformationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userInformation).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
