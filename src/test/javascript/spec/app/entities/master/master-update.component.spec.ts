import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TaskUniteTestModule } from '../../../test.module';
import { MasterUpdateComponent } from 'app/entities/master/master-update.component';
import { MasterService } from 'app/entities/master/master.service';
import { Master } from 'app/shared/model/master.model';

describe('Component Tests', () => {
  describe('Master Management Update Component', () => {
    let comp: MasterUpdateComponent;
    let fixture: ComponentFixture<MasterUpdateComponent>;
    let service: MasterService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TaskUniteTestModule],
        declarations: [MasterUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MasterUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MasterUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MasterService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Master(123);
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
        const entity = new Master();
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
