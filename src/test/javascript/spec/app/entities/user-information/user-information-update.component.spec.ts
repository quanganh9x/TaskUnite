import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TaskUniteTestModule } from '../../../test.module';
import { UserInformationUpdateComponent } from 'app/entities/user-information/user-information-update.component';
import { UserInformationService } from 'app/entities/user-information/user-information.service';
import { UserInformation } from 'app/shared/model/user-information.model';

describe('Component Tests', () => {
  describe('UserInformation Management Update Component', () => {
    let comp: UserInformationUpdateComponent;
    let fixture: ComponentFixture<UserInformationUpdateComponent>;
    let service: UserInformationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TaskUniteTestModule],
        declarations: [UserInformationUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(UserInformationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserInformationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserInformationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserInformation(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserInformation();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
