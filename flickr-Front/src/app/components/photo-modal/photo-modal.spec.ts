import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PhotoModal } from './photo-modal';

describe('PhotoModal', () => {
  let component: PhotoModal;
  let fixture: ComponentFixture<PhotoModal>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PhotoModal]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PhotoModal);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
