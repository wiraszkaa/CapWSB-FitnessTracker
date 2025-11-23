-- Insert users first (no dependencies)
INSERT INTO
    users (
        id,
        first_name,
        last_name,
        birthdate,
        email
    )
VALUES (
        1,
        'John',
        'Doe',
        '1990-05-15',
        'john.doe@example.com'
    );

INSERT INTO
    users (
        id,
        first_name,
        last_name,
        birthdate,
        email
    )
VALUES (
        2,
        'Jane',
        'Smith',
        '1985-08-22',
        'jane.smith@example.com'
    );

INSERT INTO
    users (
        id,
        first_name,
        last_name,
        birthdate,
        email
    )
VALUES (
        3,
        'Mike',
        'Johnson',
        '1992-11-30',
        'mike.johnson@example.com'
    );

INSERT INTO
    users (
        id,
        first_name,
        last_name,
        birthdate,
        email
    )
VALUES (
        4,
        'Sarah',
        'Williams',
        '1988-03-10',
        'sarah.williams@example.com'
    );

INSERT INTO
    users (
        id,
        first_name,
        last_name,
        birthdate,
        email
    )
VALUES (
        5,
        'David',
        'Brown',
        '1995-07-18',
        'david.brown@example.com'
    );

-- Insert statistics (depends on users)
INSERT INTO
    statistics (
        id,
        user_id,
        total_trainings,
        total_distance,
        total_calories_burned
    )
VALUES (1, 1, 25, 125.5, 3500);

INSERT INTO
    statistics (
        id,
        user_id,
        total_trainings,
        total_distance,
        total_calories_burned
    )
VALUES (2, 2, 30, 150.0, 4200);

INSERT INTO
    statistics (
        id,
        user_id,
        total_trainings,
        total_distance,
        total_calories_burned
    )
VALUES (3, 3, 15, 75.3, 2100);

INSERT INTO
    statistics (
        id,
        user_id,
        total_trainings,
        total_distance,
        total_calories_burned
    )
VALUES (4, 4, 40, 200.8, 5600);

INSERT INTO
    statistics (
        id,
        user_id,
        total_trainings,
        total_distance,
        total_calories_burned
    )
VALUES (5, 5, 20, 100.0, 2800);

-- Insert health metrics (depends on users)
INSERT INTO
    health_metrics (
        id,
        user_id,
        date,
        weight,
        height,
        heart_rate
    )
VALUES (
        1,
        1,
        '2025-11-01',
        75.5,
        180.0,
        72
    );

INSERT INTO
    health_metrics (
        id,
        user_id,
        date,
        weight,
        height,
        heart_rate
    )
VALUES (
        2,
        2,
        '2025-11-01',
        65.0,
        165.0,
        68
    );

INSERT INTO
    health_metrics (
        id,
        user_id,
        date,
        weight,
        height,
        heart_rate
    )
VALUES (
        3,
        3,
        '2025-11-01',
        82.3,
        175.0,
        75
    );

INSERT INTO
    health_metrics (
        id,
        user_id,
        date,
        weight,
        height,
        heart_rate
    )
VALUES (
        4,
        4,
        '2025-11-01',
        58.5,
        160.0,
        65
    );

INSERT INTO
    health_metrics (
        id,
        user_id,
        date,
        weight,
        height,
        heart_rate
    )
VALUES (
        5,
        5,
        '2025-11-01',
        90.0,
        185.0,
        80
    );

-- Insert trainings (depends on users)
INSERT INTO
    trainings (
        id,
        user_id,
        start_time,
        end_time,
        activity_type,
        distance,
        average_speed
    )
VALUES (
        1,
        1,
        '2025-11-20 08:00:00',
        '2025-11-20 09:00:00',
        'RUNNING',
        10.5,
        10.5
    );

INSERT INTO
    trainings (
        id,
        user_id,
        start_time,
        end_time,
        activity_type,
        distance,
        average_speed
    )
VALUES (
        2,
        2,
        '2025-11-20 07:00:00',
        '2025-11-20 08:30:00',
        'CYCLING',
        25.0,
        16.67
    );

INSERT INTO
    trainings (
        id,
        user_id,
        start_time,
        end_time,
        activity_type,
        distance,
        average_speed
    )
VALUES (
        3,
        3,
        '2025-11-21 18:00:00',
        '2025-11-21 19:00:00',
        'SWIMMING',
        2.0,
        2.0
    );

-- Insert workout sessions (depends on trainings)
INSERT INTO
    workout_session (
        id,
        training_id,
        timestamp,
        start_latitude,
        start_longitude,
        end_latitude,
        end_longitude,
        altitude
    )
VALUES (
        1,
        1,
        '2025-11-20 08:00:00',
        52.2297,
        21.0122,
        52.2320,
        21.0150,
        100.0
    );

INSERT INTO
    workout_session (
        id,
        training_id,
        timestamp,
        start_latitude,
        start_longitude,
        end_latitude,
        end_longitude,
        altitude
    )
VALUES (
        2,
        2,
        '2025-11-20 07:00:00',
        51.1079,
        17.0385,
        51.1100,
        17.0400,
        120.0
    );

-- Insert events
INSERT INTO
    event (
        id,
        name,
        description,
        start_time,
        end_time,
        country,
        city
    )
VALUES (
        1,
        'Warsaw Marathon 2025',
        'Annual marathon event in Warsaw',
        '2025-12-01 09:00:00',
        '2025-12-01 15:00:00',
        'Poland',
        'Warsaw'
    );

INSERT INTO
    event (
        id,
        name,
        description,
        start_time,
        end_time,
        country,
        city
    )
VALUES (
        2,
        'Cycling Challenge',
        'Mountain bike competition',
        '2025-11-25 10:00:00',
        '2025-11-25 16:00:00',
        'Poland',
        'Zakopane'
    );

-- Insert user event registrations (depends on users and events)
INSERT INTO
    user_event (id, user_id, event_id, status)
VALUES (1, 1, 1, 'REGISTERED');

INSERT INTO
    user_event (id, user_id, event_id, status)
VALUES (2, 2, 1, 'REGISTERED');

INSERT INTO
    user_event (id, user_id, event_id, status)
VALUES (3, 2, 2, 'COMPLETED');

INSERT INTO
    user_event (id, user_id, event_id, status)
VALUES (4, 3, 2, 'REGISTERED');