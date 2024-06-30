use dogWalker;
ALTER TABLE walker_service_route modify column route geometry not null ;
ALTER TABLE walker_service_route modify column created_at TIMESTAMP not null ;